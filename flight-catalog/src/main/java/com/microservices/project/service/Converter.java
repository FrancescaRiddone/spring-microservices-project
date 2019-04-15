package com.microservices.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.microservices.project.object.FlightResource;
import com.microservices.project.object.FlightTime;
import com.microservices.project.object.JourneyStage;
import com.microservices.project.model.Flight;
import com.microservices.project.model.Reservation;
import com.microservices.project.model.ReservationSeat;
import com.microservices.project.object.FlightReservationResource;

public class Converter{

	public static FlightResource convertInFlightResource(Flight theFlight) {
		
		LocalDateTime departureTime = theFlight.getDepartureTime();
		FlightTime departure = new FlightTime(departureTime.getMinute(), departureTime.getHour(), 
				departureTime.getDayOfMonth(), departureTime.getMonthValue(), departureTime.getYear());
		LocalDateTime arrivalTime = theFlight.getArrivalTime();
		FlightTime arrival = new FlightTime(arrivalTime.getMinute(), arrivalTime.getHour(), 
				arrivalTime.getDayOfMonth(), arrivalTime.getMonthValue(), arrivalTime.getYear());
		JourneyStage source = new JourneyStage(theFlight.getSourceAirport().getName(), theFlight.getSourceAirport().getCode(),
				theFlight.getSourceCity().getName(), theFlight.getSourceCountry().getName());
		JourneyStage destination = new JourneyStage(theFlight.getDestinationAirport().getName(), theFlight.getDestinationAirport().getCode(),
				theFlight.getDestinationCity().getName(), theFlight.getDestinationCountry().getName());
		
		FlightResource theFlightResource = new FlightResource(theFlight.getFlightId(), 
															theFlight.getCompany().getName(), 
															source,
															destination,
															departure, 
															arrival,
															theFlight.getAvailableEconomySeats(),
															theFlight.getAvailableBusinessSeats(),
															theFlight.getAvailableFirstSeats(),
															theFlight.getEconomySeatPrice(),
															theFlight.getBusinessSeatPrice(),
															theFlight.getFirstSeatPrice());
		
		return theFlightResource;
	}

	public static FlightReservationResource convertInReservationResource(Reservation theReservation) {
		
		FlightResource theFlightResource = convertInFlightResource(theReservation.getFlight());
		
		List<String> seats = new ArrayList<>();
		for(ReservationSeat seat: theReservation.getReservationSeats()) {
			seats.add(seat.getSeatNumber());
		}
		
		FlightReservationResource theReservationResource = new FlightReservationResource(theReservation.getId(),
																			theReservation.getUserEmail(),
																			theFlightResource,
																			theReservation.getPrice(),
																			theReservation.getSeatsType(),
																			theReservation.getSeatsNumber(),
																			seats);
		
		return theReservationResource;
	}

}

