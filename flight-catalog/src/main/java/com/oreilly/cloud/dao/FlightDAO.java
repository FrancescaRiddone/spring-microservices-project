package com.oreilly.cloud.dao;

import java.util.List;

import org.json.simple.JSONObject;

import com.oreilly.cloud.entity.Flight;

public interface FlightDAO {
	
	public List<JSONObject> getFlights(String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport,
			String destinationCity, String destinationCountry, int departureHour, int departureDay, int departureMonth, int departureYear,
			int arrivalHour, int arrivalDay, int arrivalMonth, int arrivalYear, String seatType, int seatNumber);
	
	public JSONObject getFlight(int flightId);
	
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber);

}
