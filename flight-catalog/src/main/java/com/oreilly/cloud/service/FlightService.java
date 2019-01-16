package com.oreilly.cloud.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.oreilly.cloud.entity.Flight;

public interface FlightService {
	
	public List<JSONObject> getFlights(String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport,
			String destinationCity, String destinationCountry, String seatType, int seatNumber);
	
	public JSONObject getFlight(int flightId);
	
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber);

}
