package com.oreilly.cloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.oreilly.cloud.exception.MicroserviceContactException;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.object.FlightReservationRequest;
import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.object.HotelReservationRequest;
import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	//@Autowired
	//private EurekaClient client;
	
	@Autowired
	private CartService cartService;
	
	
	@PostMapping("/flights/newFlight")
	public FlightReservationResource addFlightToCart(@RequestBody FlightReservationRequest reservationRequest, @RequestParam("userId") int userId) {
		
		System.out.println("SONO IN ADD FLIGHT IN RESERVATION HANDLER");
		System.out.println("elemento in body: " +  reservationRequest);
		System.out.println("userId passato: " + userId);
		
		
		String uri = "http://FLIGHT-CATALOG/flights/reservations/new";
		Map<String, List<String>> newParameterMap = new HashMap<>();
		FlightReservationResource response;
		
		try {
			response = restTemplate.postForObject(uri, reservationRequest, FlightReservationResource.class, newParameterMap);
			
			System.out.println("response in aggiunta volo: " + response);
			
		} catch(Exception ex) {
			throw new MicroserviceContactException();
		}
		
		cartService.addElementToCart(response, userId);

		return response;
	}
	
	@PostMapping("/hotels/newHotel")
	public HotelReservationResource addHotelToCart(@RequestBody HotelReservationRequest reservationRequest, @RequestParam("userId") int userId) {
		
		System.out.println("SONO IN ADD HOTEL IN RESERVATION HANDLER");
		System.out.println("elemento in body: " +  reservationRequest);
		
		
		String uri = "http://HOTEL-CATALOG/hotels/reservations/new";
		HotelReservationResource response;
		Map<String, List<String>> newParameterMap = new HashMap<>();
		
		try {
			response = restTemplate.postForObject(uri, reservationRequest, HotelReservationResource.class, newParameterMap);
		} catch(Exception ex) {
			throw new MicroserviceContactException();
		}
		
		cartService.addElementToCart(response, userId);

		return response;
	}
	
	@GetMapping("/flights")
	public List<FlightReservationResource> getCartFlights(@RequestParam("userId") int userId) {
		String uri = "http://FLIGHT-CATALOG/flights/reservations/";
		ResponseEntity<List<FlightReservationResource>> response;
		List<FlightReservationResource> flightInCart;
		
		List<Integer> flightIds = new ArrayList<>();
		flightIds = cartService.getUserFlightsInCart(userId);
		uri = getUriWithSetIds(uri, flightIds);
		
		try {
			response = restTemplate.exchange(uri,HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightReservationResource>>() {});
			flightInCart = response.getBody();
		} catch(Exception ex) {
			throw new MicroserviceContactException();
		}
		
		return flightInCart;
	}
	
	@GetMapping("/hotels")
	public List<HotelReservationResource> getCartHotels(@RequestParam("userId") int userId) {
		String uri = "http://HOTEL-CATALOG/hotels/reservations/";
		ResponseEntity<List<HotelReservationResource>> response;
		List<HotelReservationResource> hotelInCart;
		
		List<Integer> hotelIds = new ArrayList<>();
		hotelIds = cartService.getUserHotelsInCart(userId);
		uri = getUriWithSetIds(uri, hotelIds);
		
		try {
			response = restTemplate.exchange(uri,HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelReservationResource>>() {});
			hotelInCart = response.getBody();
		} catch(Exception ex) {
			throw new MicroserviceContactException();
		}
		
		return hotelInCart;
	}
	
	@DeleteMapping("/flights/{reservationId}")
	public String deleteFlightFromCart(@PathVariable int reservationId, @RequestParam("userId") int userId) {
		System.out.println("sono in DELETE FLIGHT FROM CART");
		
		String uri = "http://FLIGHT-CATALOG/flights/reservations/reservation/" + reservationId;
		
		try {
			restTemplate.delete(uri);
		} catch(ValidateException ex) {
			System.out.println("sono nel catch di VALIDATE EXCEPTION");
			throw new ValidateException();
		} catch(ResourceNotFoundException ex) {
			System.out.println("sono nel catch di RESOURCE NOT FOUND EXCEPTION");
			throw new ResourceNotFoundException();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			throw new MicroserviceContactException();
		}
		
		cartService.deleteElementFromCart(reservationId, "flight");
		
		//System.out.println("id dell'elemento eliminato dal carrello: " + cartService.deleteElementFromCart(reservationId, "flight"));
		System.out.println("Flight element with id " + reservationId + " delete with success from cart.");
		
		return "Flight element with id " + reservationId + " delete with success from cart.";
	}
	
	@DeleteMapping("/hotels/{reservationId}")
	public String deleteHotelFromCart(@PathVariable int reservationId, @RequestParam("userId") int userId) {
		System.out.println("sono in DELETE HOTEL FROM CART");
		
		String uri = "http://HOTEL-CATALOG/hotels/reservations/reservation/" + reservationId;
		
		try {
			restTemplate.delete(uri);
		} catch(ValidateException ex) {
			throw new ValidateException();
		} catch(ResourceNotFoundException ex) {
			throw new ResourceNotFoundException();
		} catch(Exception ex) {
			throw new MicroserviceContactException();
		}
		
		cartService.deleteElementFromCart(reservationId, "hotel");
		
		//System.out.println("id dell'elemento eliminato dal carrello: " + cartService.deleteElementFromCart(reservationId, "hotel"));
		System.out.println("Hotel element with id " + reservationId + " delete with success from cart.");
		
		return "Hotel element with id " + reservationId + " delete with success from cart.";
	}
	
	
	
	
	
	
	
	//mostra un volo del carrello
	
	
	//mostra un hotel del carrello
	
	
	//aquista volo in carrello
	
	
	//acquista hotel in carrello
	
	
	
	private String getUriWithSetIds(String uri, List<Integer> ids) {
		for(Integer id: ids) {
			if(id == ids.get(ids.size() - 1)) {
				uri = uri + id + "";
			} else {
				uri = uri + id + ",";
			}
		}
		
		return uri;
	}
	

}
