package com.oreilly.cloud.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.oreilly.cloud.exception.MicroserviceContactException;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.service.RegistryService;

@RestController
@RequestMapping("/registry")
public class ConfirmedReservationController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RegistryService registryService;
	
	
	@GetMapping("/flights")
	public List<FlightReservationResource> getRegistryFlights(@RequestParam("userId") int userId) {
		
		String uri = "http://flight-catalog/flights/reservations/";
		ResponseEntity<List<FlightReservationResource>> response;
		List<FlightReservationResource> flightInRegistry = new ArrayList<>();
		
		List<Integer> flightIds = new ArrayList<>();
		flightIds = registryService.getUserElementsInRegistry(userId, "flight");
		
		if(!flightIds.isEmpty()) {
			uri = getUriWithSetIds(uri, flightIds);
			
			try {
				response = restTemplate.exchange(uri,HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightReservationResource>>() {});
				flightInRegistry = response.getBody();
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
		
		return flightInRegistry;
	}
	
	@GetMapping("/hotels")
	public List<HotelReservationResource> getRegistryHotels(@RequestParam("userId") int userId) {
		
		String uri = "http://hotel-catalog/hotels/reservations/";
		ResponseEntity<List<HotelReservationResource>> response;
		List<HotelReservationResource> hotelInRegistry = new ArrayList<>();
		
		List<Integer> hotelIds = new ArrayList<>();
		hotelIds = registryService.getUserElementsInRegistry(userId, "hotel");
		
		if(!hotelIds.isEmpty()) {
			uri = getUriWithSetIds(uri, hotelIds);
			
			try {
				response = restTemplate.exchange(uri,HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelReservationResource>>() {});
				hotelInRegistry = response.getBody();
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
		
		return hotelInRegistry;
	}
	
	@GetMapping("/flights/{reservationId}")
	public FlightReservationResource getRegistryFlight(@PathVariable int reservationId, @RequestParam("userId") int userId) {
		String uri = "http://flight-catalog/flights/reservations/reservation/" + reservationId;
		ResponseEntity<FlightReservationResource> response;
		FlightReservationResource flightInRegistry;
		
		registryService.checkIsInUserRegistry(userId, reservationId, "flight");
		
		try {
			response = restTemplate.exchange(uri,HttpMethod.GET, null, new ParameterizedTypeReference<FlightReservationResource>() {});
			flightInRegistry = response.getBody();
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
		
		return flightInRegistry;
	}
	
	@GetMapping("/hotels/{reservationId}")
	public HotelReservationResource getCartHotel(@PathVariable int reservationId, @RequestParam("userId") int userId) {
		String uri = "http://hotel-catalog/hotels/reservations/reservation/" + reservationId;
		ResponseEntity<HotelReservationResource> response;
		HotelReservationResource hotelInRegistry;
		
		registryService.checkIsInUserRegistry(userId, reservationId, "hotel");
		
		try {
			response = restTemplate.exchange(uri,HttpMethod.GET, null, new ParameterizedTypeReference<HotelReservationResource>() {});
			hotelInRegistry = response.getBody();
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
		
		return hotelInRegistry;
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
