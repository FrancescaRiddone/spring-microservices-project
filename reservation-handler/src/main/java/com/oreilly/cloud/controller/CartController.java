package com.oreilly.cloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.oreilly.cloud.exception.MicroserviceContactException;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ResourceUnavailableException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.object.BankDetails;
import com.oreilly.cloud.object.FlightReservationRequest;
import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.object.HotelReservationRequest;
import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CartService cartService;
	
	
	@PostMapping("/flights/newFlight")
	public FlightReservationResource addFlightToCart(@RequestBody FlightReservationRequest reservationRequest, @RequestParam("userId") int userId) {
		String uri = "http://FLIGHT-CATALOG/flights/reservations/new";
		Map<String, List<String>> newParameterMap = new HashMap<>();
		FlightReservationResource response;
		
		try {
			response = restTemplate.postForObject(uri, reservationRequest, FlightReservationResource.class, newParameterMap);
		
		} catch(ValidateException ex) {
			System.out.println("sono nel catch di VALIDATE EXCEPTION");
			throw new ValidateException();
		} catch(ResourceUnavailableException ex) {
			System.out.println("sono nel catch di RESOURCE UNAVAILABLE EXCEPTION");
			throw new ResourceUnavailableException();
		} catch(ResourceNotFoundException ex) {
			System.out.println("sono nel catch di RESOURCE NOT FOUND EXCEPTION");
			throw new ResourceNotFoundException();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			throw new MicroserviceContactException();
		}
		
		cartService.addElementToCart(response, userId);

		return response;
	}
	
	@PostMapping("/hotels/newHotel")
	public HotelReservationResource addHotelToCart(@RequestBody HotelReservationRequest reservationRequest, @RequestParam("userId") int userId) {
		String uri = "http://HOTEL-CATALOG/hotels/reservations/new";
		HotelReservationResource response;
		Map<String, List<String>> newParameterMap = new HashMap<>();
		
		try {
			response = restTemplate.postForObject(uri, reservationRequest, HotelReservationResource.class, newParameterMap);
		} catch(ValidateException ex) {
			System.out.println("sono nel catch di VALIDATE EXCEPTION");
			throw new ValidateException();
		} catch(ResourceUnavailableException ex) {
			System.out.println("sono nel catch di RESOURCE UNAVAILABLE EXCEPTION");
			throw new ResourceUnavailableException();
		} catch(ResourceNotFoundException ex) {
			System.out.println("sono nel catch di RESOURCE NOT FOUND EXCEPTION");
			throw new ResourceNotFoundException();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			throw new MicroserviceContactException();
		}
		
		cartService.addElementToCart(response, userId);

		return response;
	}
	
	@GetMapping("/flights/{reservationId}")
	public FlightReservationResource getCartFlight(@PathVariable int reservationId, @RequestParam("userId") int userId) {
		String uri = "http://FLIGHT-CATALOG/flights/reservations/reservation/" + reservationId;
		ResponseEntity<FlightReservationResource> response;
		FlightReservationResource flightInCart;
		
		cartService.checkIsInUserCart(userId, reservationId, "flight");
		
		try {
			response = restTemplate.exchange(uri,HttpMethod.GET, null, new ParameterizedTypeReference<FlightReservationResource>() {});
			flightInCart = response.getBody();
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
		
		return flightInCart;
	}
	
	@GetMapping("/hotels/{reservationId}")
	public HotelReservationResource getCartHotel(@PathVariable int reservationId, @RequestParam("userId") int userId) {
		String uri = "http://HOTEL-CATALOG/hotels/reservations/reservation/" + reservationId;
		ResponseEntity<HotelReservationResource> response;
		HotelReservationResource hotelInCart;
		
		cartService.checkIsInUserCart(userId, reservationId, "hotel");
		
		try {
			response = restTemplate.exchange(uri,HttpMethod.GET, null, new ParameterizedTypeReference<HotelReservationResource>() {});
			hotelInCart = response.getBody();
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
		
		return hotelInCart;
	}
	
	@GetMapping("/flights")
	public List<FlightReservationResource> getCartFlights(@RequestParam("userId") int userId) {
		String uri = "http://FLIGHT-CATALOG/flights/reservations/";
		ResponseEntity<List<FlightReservationResource>> response;
		List<FlightReservationResource> flightInCart;
		
		List<Integer> flightIds = new ArrayList<>();
		flightIds = cartService.getUserElementsInCart(userId, "flight");
		uri = getUriWithSetIds(uri, flightIds);
		
		try {
			response = restTemplate.exchange(uri,HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightReservationResource>>() {});
			flightInCart = response.getBody();
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
		
		return flightInCart;
	}
	
	@GetMapping("/hotels")
	public List<HotelReservationResource> getCartHotels(@RequestParam("userId") int userId) {
		String uri = "http://HOTEL-CATALOG/hotels/reservations/";
		ResponseEntity<List<HotelReservationResource>> response;
		List<HotelReservationResource> hotelInCart;
		
		List<Integer> hotelIds = new ArrayList<>();
		hotelIds = cartService.getUserElementsInCart(userId, "hotel");
		uri = getUriWithSetIds(uri, hotelIds);
		
		try {
			response = restTemplate.exchange(uri,HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelReservationResource>>() {});
			hotelInCart = response.getBody();
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
		
		return hotelInCart;
	}
	
	@DeleteMapping("/flights/{reservationId}")
	public String deleteFlightFromCart(@PathVariable int reservationId, @RequestParam("userId") int userId) {
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
		
		return "Flight element with id " + reservationId + " delete with success from cart.";
	}
	
	@DeleteMapping("/hotels/{reservationId}")
	public String deleteHotelFromCart(@PathVariable int reservationId, @RequestParam("userId") int userId) {
		String uri = "http://HOTEL-CATALOG/hotels/reservations/reservation/" + reservationId;
		
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
		
		cartService.deleteElementFromCart(reservationId, "hotel");
		
		return "Hotel element with id " + reservationId + " delete with success from cart.";
	}
	
	@PostMapping("/flights/confirmedFlight/{reservationId}")
	public String buyFlightInCart(@PathVariable int reservationId, @RequestBody BankDetails bankDetails, @RequestParam("userId") int userId) {
		String uri = "http://FLIGHT-CATALOG/flights/reservations/confirmedReservation/" + reservationId;
		Map<String, List<String>> newParameterMap = new HashMap<>();
		
		cartService.checkIsInUserCart(userId, reservationId, "flight");
		cartService.checkBankDetails(bankDetails);
		
		try {
			restTemplate.put(uri, null, newParameterMap);
		
		} catch(ValidateException ex) {
			System.out.println("sono nel catch di VALIDATE EXCEPTION");
			throw new ValidateException();
		} catch(ResourceNotFoundException ex) {
			System.out.println("sono nel catch di RESOURCE NOT FOUND EXCEPTION");
			throw new ResourceNotFoundException();
		} catch(ResourceUnavailableException ex) {
			System.out.println("sono nel catch di RESOURCE UNAVAILABLE EXCEPTION");
			throw new ResourceUnavailableException();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			throw new MicroserviceContactException();
		}
		
		cartService.confirmReservationInCart(userId, reservationId, "flight");
		
		return "Flight reservation successfully confirmed.";
	}
	
	@PostMapping("/hotels/confirmedHotel/{reservationId}")
	public String buyHoteltInCart(@PathVariable int reservationId, @RequestBody BankDetails bankDetails, @RequestParam("userId") int userId) {
		String uri = "http://HOTEL-CATALOG/hotels/reservations/confirmedReservation/" + reservationId;
		Map<String, List<String>> newParameterMap = new HashMap<>();
		
		cartService.checkIsInUserCart(userId, reservationId, "hotel");
		cartService.checkBankDetails(bankDetails);
		
		try {
			restTemplate.put(uri, null, newParameterMap);
		
		} catch(ValidateException ex) {
			System.out.println("sono nel catch di VALIDATE EXCEPTION");
			throw new ValidateException();
		} catch(ResourceNotFoundException ex) {
			System.out.println("sono nel catch di RESOURCE NOT FOUND EXCEPTION");
			throw new ResourceNotFoundException();
		} catch(ResourceUnavailableException ex) {
			System.out.println("sono nel catch di RESOURCE UNAVAILABLE EXCEPTION");
			throw new ResourceUnavailableException();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			throw new MicroserviceContactException();
		}
		
		cartService.confirmReservationInCart(userId, reservationId, "hotel");
		
		return "Hotel reservation successfully confirmed.";
	}

	
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
