package com.microservices.project.service;

import java.util.List;

import com.microservices.project.model.Flight;
import com.microservices.project.object.FlightResource;
import com.microservices.project.object.SearchFlightRequest;

public interface FlightService {
	
	public List<FlightResource> getFlights(SearchFlightRequest searchFlightRequest);
	
	public FlightResource getFlight(int flightId);
	
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber);
	
	public void updateAvailableSeats(int flightId, String seatClass, int seatNumber);

}
