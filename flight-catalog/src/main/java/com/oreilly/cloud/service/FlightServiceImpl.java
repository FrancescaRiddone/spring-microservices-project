package com.oreilly.cloud.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
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
	public List<JSONObject> getFlights(String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport,
			String destinationCity, String destinationCountry, String seatType, int seatNumber) {
		
		if(!checkParamsForGetFlights(sourceAirport, sourceCity, sourceCountry, destinationAirport, destinationCity, 
				destinationCountry, seatType, seatNumber)) {
			return  new ArrayList<>();
		}
		
		List<JSONObject> theRequiredFlights = flightDAO.getFlights(sourceAirport, sourceCity, sourceCountry, destinationAirport, 
				destinationCity, destinationCountry, seatType, seatNumber);
		
		if(theRequiredFlights == null) {
			theRequiredFlights = new ArrayList<>();
		}
		
		return theRequiredFlights;
	}

	@Override
	@Transactional
	public JSONObject getFlight(int flightId) {
		if(flightId <= 0) {
			return new JSONObject();
		}
		
		JSONObject theFlight = flightDAO.getFlight(flightId);
		
		if(theFlight == null) {
			theFlight = new JSONObject();
		}
		
		return theFlight;
	}

	@Override
	@Transactional
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber) {
		if(flightId <= 0 || seatClass == null || seatClass.equals("") || seatNumber == 0) {
			return null;
		}
		
		return flightDAO.checkFlightAvailability(flightId, seatClass, seatNumber);
	}
	
	private boolean checkParamsForGetFlights(String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport,
			String destinationCity, String destinationCountry, String seatType, int seatNumber) {
		
		if(sourceAirport == null && sourceCity == null && sourceCountry == null && destinationAirport == null &&
			destinationCity == null && destinationCountry == null && seatType == null && seatNumber == 0) {
			return false;
		}
		
		return true;
	}

	
}
