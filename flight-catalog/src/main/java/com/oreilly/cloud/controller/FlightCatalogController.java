package com.oreilly.cloud.controller;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.entity.Reservation;
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
	public List<JSONObject> getFlights(@RequestParam("sourceAirport") String sourceAirport, 
									@RequestParam("sourceCity") String sourceCity,
									@RequestParam("sourceCountry") String sourceCountry, 
									@RequestParam("destinationAirport") String destinationAirport,
									@RequestParam("destinationCity") String destinationCity, 
									@RequestParam("destinationCountry") String destinationCountry,
									@RequestParam("seatType") String seatType, 
									@RequestParam("seatNumber") int seatNumber) {
		
		return flightService.getFlights(sourceAirport, sourceCity, sourceCountry, 
				destinationAirport, destinationCity, destinationCountry, seatType, seatNumber);
	}
	
	@GetMapping("/flight")
	public JSONObject getFlight(@RequestParam("flightId") int flightId) {
		return flightService.getFlight(flightId);
	}
	
	@GetMapping("/reservations")
	public List<JSONObject> getReservations(@RequestParam List<Integer> reservationIds) {
		
		List<JSONObject> theReservationsJSON = new ArrayList<>();
		for(int reservationId: reservationIds) {
			JSONObject theReservationJSON = reservationService.getReservationJSON(reservationId);
			if(theReservationJSON != null) {
				theReservationsJSON.add(theReservationJSON);
			}
		}
		return theReservationsJSON;
	}
	
	@GetMapping("/reservations/reservation")
	public JSONObject getReservation(@RequestParam("reservationId") int reservationId) {
		return reservationService.getReservationJSON(reservationId);
	}
	
	@PostMapping("/reservations/newReservation")
	public JSONObject createReservation(@RequestParam("flightId") int flightId,
									@RequestParam("userName") String userName,
									@RequestParam("userSurname") String userSurname,
									@RequestParam("seatClass") String seatClass,
									@RequestParam("seatNumber") int seatNumber) {
		
		Flight availableFlight = flightService.checkFlightAvailability(flightId, seatClass, seatNumber);
		if(availableFlight == null) {
			return new JSONObject();
		}
		
		return reservationService.saveReservation(availableFlight, userName, userSurname, seatClass, seatNumber, false);
	}
	
	@PostMapping("/reservations/reserve")
	public JSONObject confirmeReservation(@RequestParam("reservationId") int reservationId) {
		Reservation theReservation = reservationService.getReservation(reservationId);
		if(theReservation == null) {
			return new JSONObject();
		}
		Flight availableFlight = flightService.checkFlightAvailability(theReservation.getFlight().getFlightId(), 
				theReservation.getSeatsType(), theReservation.getSeatsNumber());
		if(availableFlight == null) {
			return new JSONObject();
		}
		
		return reservationService.saveReservation(availableFlight, theReservation.getUserName(), theReservation.getUserSurname(), 
				theReservation.getSeatsType(), theReservation.getSeatsNumber(), true);
	}
	
	
	
}
