package com.oreilly.cloud.dao;

import org.json.simple.JSONObject;

import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.entity.Reservation;

public class UtilsForObjectsConversion {

	@SuppressWarnings("unchecked")
	public static JSONObject createJSONForFlight(Flight theFlight){
		JSONObject theFlightJSON = new JSONObject();
		
		theFlightJSON.put("flightId", theFlight.getId());
		theFlightJSON.put("company", theFlight.getCompany().getName());
		theFlightJSON.put("sourceAirport", theFlight.getSourceAirport().getName());
		theFlightJSON.put("sourceAirportCode", theFlight.getSourceAirport().getCode());
		theFlightJSON.put("sourceCity", theFlight.getSourceCity().getName());
		theFlightJSON.put("sourceCountry", theFlight.getSourceCountry().getName());
		theFlightJSON.put("destinationAirport", theFlight.getDestinationAirport().getName());
		theFlightJSON.put("destinationAirportCode", theFlight.getDestinationAirport().getCode());
		theFlightJSON.put("destinationCity", theFlight.getDestinationCity().getName());
		theFlightJSON.put("destinationCountry", theFlight.getDestinationCountry().getName());
		theFlightJSON.put("availableEconomySeats", new Integer(theFlight.getAvailableEconomySeats()));
		theFlightJSON.put("availableBusinessSeats", new Integer(theFlight.getAvailableBusinessSeats()));
		theFlightJSON.put("availableFirstSeats", new Integer(theFlight.getAvailableFirstSeats()));
		theFlightJSON.put("economySeatPrice", new Double(theFlight.getEconomySeatPrice()));
		theFlightJSON.put("businessSeatPrice", new Double(theFlight.getBusinessSeatPrice()));
		theFlightJSON.put("firstSeatPrice", new Double(theFlight.getFirstSeatPrice()));
		theFlightJSON.put("departureTime", theFlight.getDepartureTime().toString());
		theFlightJSON.put("arrivalTime", theFlight.getArrivalTime().toString());
		
		return theFlightJSON;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject createJSONForReservation(Reservation theReservation){
		JSONObject theReservationJSON = new JSONObject();
		JSONObject theFlightJSON = createJSONForFlight(theReservation.getFlight()); 
		
		theReservationJSON.put("flight", theFlightJSON);
		theReservationJSON.put("reservationId", theReservation.getId());
		theReservationJSON.put("price", theReservation.getPrice());
		theReservationJSON.put("seatsType", theReservation.getSeatsType());
		theReservationJSON.put("seatsNumber", theReservation.getSeatsNumber());
		
		return theReservationJSON;
	}

	
}
