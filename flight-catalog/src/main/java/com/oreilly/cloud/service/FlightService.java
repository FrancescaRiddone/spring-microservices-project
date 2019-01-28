package com.oreilly.cloud.service;

import java.util.List;

import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.SearchFlightRequest;

public interface FlightService {
	
	public List<FlightResource> getFlights(SearchFlightRequest searchFlightRequest);
	
	public FlightResource getFlight(int flightId);
	
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber);
	
	public void updateAvailableSeats(int flightId, String seatClass, int seatNumber);

}
