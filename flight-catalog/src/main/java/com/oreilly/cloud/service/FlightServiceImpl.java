package com.oreilly.cloud.service;

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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public JSONObject getFlights(String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport,
			String destinationCity, String destinationCountry, int departureHour, int departureDay, int departureMonth, int departureYear,
			int arrivalHour, int arrivalDay, int arrivalMonth, int arrivalYear, String seatType, int seatNumber) {
		
		if(!checkParamsForGetFlights(sourceAirport, sourceCity, sourceCountry, destinationAirport, destinationCity, 
				destinationCountry, seatType, seatNumber)) {
			return  new JSONObject();
		}
		JSONObject requiredFlights = new JSONObject();
		requiredFlights.put("flights", flightDAO.getFlights(sourceAirport, sourceCity, sourceCountry, 
				destinationAirport, destinationCity, destinationCountry, departureHour, departureDay, departureMonth, departureYear,
				arrivalHour, arrivalDay, arrivalMonth, arrivalYear, seatType, seatNumber));
		
		return requiredFlights;
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
