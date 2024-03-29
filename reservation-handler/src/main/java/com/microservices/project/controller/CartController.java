package com.microservices.project.controller;

import java.io.IOException;
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

import com.microservices.project.exception.MicroserviceContactException;
import com.microservices.project.exception.ResourceNotFoundException;
import com.microservices.project.exception.ResourceUnavailableException;
import com.microservices.project.exception.ValidateException;
import com.microservices.project.object.BookingConfirmation;
import com.microservices.project.object.FlightReservationRequest;
import com.microservices.project.object.FlightReservationResource;
import com.microservices.project.object.HotelReservationRequest;
import com.microservices.project.object.HotelReservationResource;
import com.microservices.project.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CartService cartService;
	
	
	@GetMapping("/flights/{reservationId}")
	public FlightReservationResource getCartFlight(@PathVariable int reservationId, @RequestParam("userId") int userId) {
		String uri = "http://flight-catalog/flights/reservations/reservation/" + reservationId;
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
	public HotelReservationResource getCartHotel(@PathVariable int reservationId, @RequestParam("userId") int userId) throws IOException {
		String uri = "http://hotel-catalog/hotels/reservations/reservation/" + reservationId;
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
			throw new IOException();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			throw new MicroserviceContactException();
		}
		
		return hotelInCart;
	}
	
	@GetMapping("/flights")
	public List<FlightReservationResource> getCartFlights(@RequestParam("userId") int userId) {
		String uri = "http://flight-catalog/flights/reservations/";
		ResponseEntity<List<FlightReservationResource>> response;
		List<FlightReservationResource> flightInCart = new ArrayList<>();
		
		List<Integer> flightIds = new ArrayList<>();
		flightIds = cartService.getUserElementsInCart(userId, "flight");
		
		if(!flightIds.isEmpty()) {
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
		}
		
		return flightInCart;
	}
	
	@GetMapping("/hotels")
	public List<HotelReservationResource> getCartHotels(@RequestParam("userId") int userId) {
		String uri = "http://hotel-catalog/hotels/reservations/";
		ResponseEntity<List<HotelReservationResource>> response;
		List<HotelReservationResource> hotelInCart = new ArrayList<>();
		
		List<Integer> hotelIds = new ArrayList<>();
		hotelIds = cartService.getUserElementsInCart(userId, "hotel");
		
		if(!hotelIds.isEmpty()) {
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
		}
		
		return hotelInCart;
	}
	
	@PostMapping("/flights/newFlight")
	public FlightReservationResource addFlightToCart(@RequestBody FlightReservationRequest reservationRequest, @RequestParam("userId") int userId) {
		String uri = "http://flight-catalog/flights/reservations/new";
		FlightReservationResource response;
		
		try {
			response = restTemplate.postForObject(uri, reservationRequest, FlightReservationResource.class, new HashMap<>());
		
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
		String uri = "http://hotel-catalog/hotels/reservations/new";
		HotelReservationResource response;
		
		try {
			response = restTemplate.postForObject(uri, reservationRequest, HotelReservationResource.class, new HashMap<>());
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
	
	@DeleteMapping("/flights/{reservationId}")
	public String deleteFlightFromCart(@PathVariable int reservationId, @RequestParam("userId") int userId) {
		String uri = "http://flight-catalog/flights/reservations/reservation/" + reservationId;
		
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
		
		return "Flight element with id " + reservationId + " has been successfully deleted from cart.";
	}
	
	@DeleteMapping("/hotels/{reservationId}")
	public String deleteHotelFromCart(@PathVariable int reservationId, @RequestParam("userId") int userId) {
		String uri = "http://hotel-catalog/hotels/reservations/reservation/" + reservationId;
		
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
		
		return "Hotel element with id " + reservationId + " has been successfully deleted from cart.";
	}
	
	@PostMapping("/flights/confirmation")
	public String buyFlightInCart(@RequestBody BookingConfirmation bookingConfirmation, @RequestParam("userId") int userId) {
		String uri = "http://flight-catalog/flights/reservations/confirmedReservation/" + bookingConfirmation.getReservationId();
		Map<String, List<String>> newParameterMap = new HashMap<>();
		
		cartService.checkIsInUserCart(userId, bookingConfirmation.getReservationId(), "flight");
		cartService.checkBankDetails(bookingConfirmation.getBankDetails());
		
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
		
		cartService.confirmReservationInCart(userId, bookingConfirmation.getReservationId(), "flight");
		
		return "Flight reservation successfully confirmed.";
	}
	
	@PostMapping("/hotels/confirmation")
	public String buyHotelInCart(@RequestBody BookingConfirmation bookingConfirmation, @RequestParam("userId") int userId) {
		String uri = "http://hotel-catalog/hotels/reservations/confirmedReservation/" + bookingConfirmation.getReservationId();
		Map<String, List<String>> newParameterMap = new HashMap<>();
		
		cartService.checkIsInUserCart(userId, bookingConfirmation.getReservationId(), "hotel");
		cartService.checkBankDetails(bookingConfirmation.getBankDetails());
		
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
		
		cartService.confirmReservationInCart(userId, bookingConfirmation.getReservationId(), "hotel");
		
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
