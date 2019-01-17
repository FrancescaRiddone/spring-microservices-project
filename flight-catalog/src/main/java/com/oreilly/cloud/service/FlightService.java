package com.oreilly.cloud.service;


import org.json.simple.JSONObject;

import com.oreilly.cloud.entity.Flight;

public interface FlightService {
	
	public JSONObject getFlights(String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport,
			String destinationCity, String destinationCountry, int departureHour, int departureDay, int departureMonth, int departureYear,
			int arrivalHour, int arrivalDay, int arrivalMonth, int arrivalYear, String seatType, int seatNumber);
	
	public JSONObject getFlight(int flightId);
	
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber);

}
