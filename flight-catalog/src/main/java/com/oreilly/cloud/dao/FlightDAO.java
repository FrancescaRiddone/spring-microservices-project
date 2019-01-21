package com.oreilly.cloud.dao;

import java.util.List;

import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.object.SearchFlightRequest;

public interface FlightDAO {
	
	public List<Flight> getFlights(SearchFlightRequest searchFlightRequest);
	
	public Flight getFlight(int flightId);
	
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber);

}
