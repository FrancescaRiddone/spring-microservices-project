package com.oreilly.cloud.service;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.SearchFlightRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.dao.FlightDAO;
import com.oreilly.cloud.entity.Flight;

@Service
public class FlightServiceImpl implements FlightService{

	@Autowired
	private FlightDAO flightDAO;
	
	
	@Override
	@Transactional
	public List<FlightResource> getFlights(SearchFlightRequest searchFlightRequest) {
		checkParamsForGetFlights(searchFlightRequest);

		List<FlightResource> theRequiredFlights = new ArrayList<>();
		List<Flight> foundedFlights = flightDAO.getFlights(searchFlightRequest);
		if(foundedFlights == null) {
			throw new ResourceNotFoundException();
		}
		for(Flight theFlight: foundedFlights) {
			theRequiredFlights.add(com.oreilly.cloud.service.Converter.convertInFlightResource(theFlight));
		}
		
		return theRequiredFlights;
	}

	@Override
	@Transactional
	public FlightResource getFlight(int flightId) {
		Flight theFlight = flightDAO.getFlight(flightId);

		if(theFlight == null) {
			throw new ResourceNotFoundException();
		}
		FlightResource flightResource = com.oreilly.cloud.service.Converter.convertInFlightResource(theFlight);
		
		return flightResource;
	}

	@Override
	@Transactional
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber) {
		if(flightId <= 0 || seatClass == null || seatClass.equals("") || seatNumber == 0) {
			return null;
		}
		
		return flightDAO.checkFlightAvailability(flightId, seatClass, seatNumber);
	}
	
	private void checkParamsForGetFlights(SearchFlightRequest searchFlightRequest) {
		if(searchFlightRequest.getSource().getAirportName() == null && searchFlightRequest.getSource().getCity() == null && 
				searchFlightRequest.getSource().getCountry() == null && searchFlightRequest.getDestination().getAirportName() == null &&
				searchFlightRequest.getDestination().getCity() == null && searchFlightRequest.getDestination().getCountry() == null && 
				searchFlightRequest.getSeatType() == null && searchFlightRequest.getSeatNumber() == 0) {

			throw new ValidateException();
		}
	}
	
	
}
