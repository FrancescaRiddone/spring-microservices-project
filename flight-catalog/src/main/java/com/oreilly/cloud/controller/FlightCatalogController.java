package com.oreilly.cloud.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ResourceUnavailableException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.FlightReservationRequest;
import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.object.SearchFlightRequest;
import com.oreilly.cloud.service.FlightService;
import com.oreilly.cloud.service.ReservationService;

@RestController
@RequestMapping("/flights")
public class FlightCatalogController {
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private ReservationService reservationService;
	
	
	@GetMapping("/flight/{flightId}")
	public FlightResource getFlight(@PathVariable int flightId) throws ValidateException, ResourceNotFoundException {
		
		return flightService.getFlight(flightId);
	}
	
	@PostMapping("/requiredFlights")
	public List<FlightResource> getFlights(@RequestBody SearchFlightRequest searchFlightRequest) throws ValidateException, ResourceNotFoundException {
		
		return flightService.getFlights(searchFlightRequest);
	}
	
	@GetMapping("/reservations/reservation/{reservationId}")
	public FlightReservationResource getReservation(@PathVariable int reservationId) throws ValidateException, ResourceNotFoundException {
		
		return reservationService.getReservationResource(reservationId);
	}
	
	@GetMapping("/reservations/{reservationIds}")
	public List<FlightReservationResource> getReservations(@PathVariable List<Integer> reservationIds) throws ValidateException, ResourceNotFoundException {
		List<FlightReservationResource> theReservations = new ArrayList<>();
		for(int reservationId: reservationIds) {
			FlightReservationResource theReservation = reservationService.getReservationResource(reservationId);
			if(theReservation != null) {
				theReservations.add(theReservation);
			}
		}
		if(theReservations.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		return theReservations;
	}
	
	@PostMapping("/reservations/new")
	public FlightReservationResource createReservation(@RequestBody FlightReservationRequest flightReservationRequest) 
			throws ResourceNotFoundException, ValidateException, ResourceUnavailableException {
		
		System.out.println("sono in metodo per nuova reservation");
		
		Flight availableFlight = flightService.checkFlightAvailability(flightReservationRequest.getFlightId(), 
				flightReservationRequest.getSeatClass(), flightReservationRequest.getSeatNumber());
		
		Reservation theNewReservation = new Reservation(availableFlight, flightReservationRequest.getUserEmail(), 0.0, flightReservationRequest.getSeatClass(),
				flightReservationRequest.getSeatNumber(), false);
		
		return reservationService.saveReservation(theNewReservation);
	}
	
	@PutMapping("/reservations/confirmedReservation/{reservationId}")
	public String confirmReservation(@PathVariable int reservationId) throws ValidateException, 
			ResourceNotFoundException, ResourceUnavailableException {
		
		Reservation theReservation = reservationService.getReservation(reservationId);
		
		System.out.println("trovata reservation con id " + reservationId +  ": " + theReservation);
		
		flightService.checkFlightAvailability(theReservation.getFlight().getFlightId(), 
					theReservation.getSeatsType(), theReservation.getSeatsNumber());
		
		theReservation.setConfirmed(true);
		reservationService.saveReservation(theReservation);
		
		System.out.println("reservation confermata");
		
		flightService.updateAvailableSeats(theReservation.getFlight().getFlightId(), theReservation.getSeatsType(), 
					theReservation.getSeatsNumber());
		
		System.out.println("aggiornati posti disponibili");
		
		return "Flight reservation successfully confirmed.";
	}
	
	@DeleteMapping("/reservations/reservation/{reservationId}")
	public void deleteReservation(@PathVariable int reservationId) throws ValidateException, ResourceNotFoundException {
		
		reservationService.deleteReservation(reservationId);
	}
	
	
}
