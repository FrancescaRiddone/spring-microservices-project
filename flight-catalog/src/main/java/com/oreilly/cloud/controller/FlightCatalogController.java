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

import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.entity.Reservation;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.ReservationResource;
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
	
	
	@GetMapping("/requiredFlights")
	public List<FlightResource> getFlights(@RequestBody SearchFlightRequest searchFlightRequest) {
		return flightService.getFlights(searchFlightRequest);
	}
	
	@GetMapping("/flight")
	public FlightResource getFlight(@RequestParam("flightId") int flightId) {
		return flightService.getFlight(flightId);
	}
	
	@GetMapping("/reservations")
	public List<ReservationResource> getReservations(@RequestParam List<Integer> reservationIds) {
		List<ReservationResource> theReservations = new ArrayList<>();
		for(int reservationId: reservationIds) {
			ReservationResource theReservation = reservationService.getReservationResource(reservationId);
			if(theReservation != null) {
				theReservations.add(theReservation);
			}
		}
		if(theReservations.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		return theReservations;
	}
	
	@GetMapping("/reservations/reservation")
	public ReservationResource getReservation(@RequestParam("reservationId") int reservationId) {
		ReservationResource theReservation = reservationService.getReservationResource(reservationId);
		if(theReservation == null) {
			throw new ResourceNotFoundException();
		}
		
		return theReservation;
	}
	
	@PostMapping("/reservations/newReservation")
	public ReservationResource createReservation(@RequestParam("flightId") int flightId,
									@RequestParam("userName") String userName,
									@RequestParam("userSurname") String userSurname,
									@RequestParam("seatClass") String seatClass,
									@RequestParam("seatNumber") int seatNumber) {
		
		Flight availableFlight = flightService.checkFlightAvailability(flightId, seatClass, seatNumber);
		if(availableFlight == null) {
			throw new ResourceNotFoundException();
		}
		
		Reservation theNewReservation = new Reservation(availableFlight, userName, userSurname, 0.0, seatClass,
				seatNumber, false);
		
		return reservationService.saveReservation(theNewReservation);
	}
	
	@PostMapping("/reservations/reserve")
	public ReservationResource confirmeReservation(@RequestParam("reservationId") int reservationId) {
		Reservation theConfirmedReservation = reservationService.getReservation(reservationId);
		
		Flight availableFlight = flightService.checkFlightAvailability(theConfirmedReservation.getFlight().getId(), 
				theConfirmedReservation.getSeatsType(), theConfirmedReservation.getSeatsNumber());
		if(availableFlight == null) {
			throw new ResourceNotFoundException();
		}
		
		return reservationService.saveReservation(theConfirmedReservation);
	}
	
	
	
}
