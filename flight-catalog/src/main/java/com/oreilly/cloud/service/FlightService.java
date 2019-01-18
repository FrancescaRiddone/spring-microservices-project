package com.oreilly.cloud.service;


import org.json.simple.JSONObject;

import com.oreilly.cloud.entity.Flight;

public interface FlightService {
	
	public JSONObject getFlights(SearchFlightRequest searchFlightRequest);
	
	public JSONObject getFlight(int flightId);
	
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber);

}
