package com.oreilly.cloud.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oreilly.cloud.exception.ResourceNotFoundException;
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
	
	
	@GetMapping("/flight")
	public FlightResource getFlight(@RequestParam("flightId") int flightId) throws ResourceNotFoundException {
		FlightResource resource = flightService.getFlight(flightId);
		
		return resource;
	}
	
	@PostMapping("/requiredFlights")
	public List<FlightResource> getFlights(@RequestBody SearchFlightRequest searchFlightRequest) throws ResourceNotFoundException, ValidateException {
		return flightService.getFlights(searchFlightRequest);
	}
	
	@GetMapping("/reservations/reservation")
	public FlightReservationResource getReservation(@RequestParam("reservationId") int reservationId) throws ResourceNotFoundException {
		FlightReservationResource theReservation = reservationService.getReservationResource(reservationId);
		
		return theReservation;
	}
	
	@GetMapping("/reservations")
	public List<FlightReservationResource> getReservations(@RequestParam("reservationIds") List<Integer> reservationIds) throws ResourceNotFoundException {
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
			throws ResourceNotFoundException, ValidateException{
		
		Flight availableFlight = flightService.checkFlightAvailability(flightReservationRequest.getFlightId(), 
				flightReservationRequest.getSeatClass(), flightReservationRequest.getSeatNumber());
		
		if(availableFlight == null) {
			throw new ResourceNotFoundException();
		}
		Reservation theNewReservation = new Reservation(availableFlight, flightReservationRequest.getUserName(), 
				flightReservationRequest.getUserSurname(), 0.0, flightReservationRequest.getSeatClass(),
				flightReservationRequest.getSeatNumber(), false);
		
		return reservationService.saveReservation(theNewReservation);
	}
	
	@GetMapping("/reservations/confirm")
	public FlightReservationResource confirmReservation(@RequestParam int reservationId) throws ResourceNotFoundException, ValidateException {
		Reservation theReservation = reservationService.getReservation(reservationId);
		
		if(flightService.checkFlightAvailability(theReservation.getFlight().getFlightId(), 
					theReservation.getSeatsType(), theReservation.getSeatsNumber()) == null) {
			
				throw new ResourceNotFoundException();
		}
		
		theReservation.setConfirmed(true);
		FlightReservationResource theReservationResource = reservationService.saveReservation(theReservation);
		flightService.updateAvailableSeats(theReservation.getFlight().getFlightId(), theReservation.getSeatsType(), 
					theReservation.getSeatsNumber());
		
		return theReservationResource;
	}
	
	
}
