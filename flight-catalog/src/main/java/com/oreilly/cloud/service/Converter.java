package com.oreilly.cloud.service;

import java.util.Calendar;
import java.util.Date;

import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.JourneyStage;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.object.FlightReservationResource;


public class Converter{

	public static FlightResource convertInFlightResource(Flight theFlight) {
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(new Date(theFlight.getDepartureTime().getTime()));
		JourneyStage departure = new JourneyStage(theFlight.getSourceAirport().getName(), theFlight.getSourceAirport().getCode(),
				theFlight.getSourceCity().getName(), theFlight.getSourceCountry().getName(), cal.get(Calendar.MINUTE),
				cal.get(Calendar.HOUR_OF_DAY) - 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.YEAR));
		
		cal.setTime(new Date(theFlight.getArrivalTime().getTime()));
		JourneyStage arrival = new JourneyStage(theFlight.getDestinationAirport().getName(), theFlight.getDestinationAirport().getCode(),
				theFlight.getDestinationCity().getName(), theFlight.getDestinationCountry().getName(), cal.get(Calendar.MINUTE),
				cal.get(Calendar.HOUR_OF_DAY) - 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.YEAR));
		
		FlightResource theFlightResource = new FlightResource(theFlight.getFlightId(), 
															theFlight.getCompany().getName(), 
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
		
		FlightReservationResource theReservationResource = new FlightReservationResource(theReservation.getId(),
																			theFlightResource,
																			theReservation.getPrice(),
																			theReservation.getSeatsType(),
																			theReservation.getSeatsNumber());
		
		return theReservationResource;
	}

}
