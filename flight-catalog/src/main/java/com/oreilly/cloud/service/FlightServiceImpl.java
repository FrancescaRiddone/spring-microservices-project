package com.oreilly.cloud.service;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
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
	public JSONObject getFlights(SearchFlightRequest searchFlightRequest) {
		
		checkParamsForGetFlights(searchFlightRequest);

		JSONObject requiredFlights = new JSONObject();
		requiredFlights.put("flights", flightDAO.getFlights(searchFlightRequest.getSourceAirport(), searchFlightRequest.getSourceCity(), searchFlightRequest.getSourceCountry(),
				searchFlightRequest.getDestinationAirport(), searchFlightRequest.getDestinationCity(), searchFlightRequest.getDestinationCountry(), searchFlightRequest.getDepartureHour(), searchFlightRequest.getDepartureDay(), searchFlightRequest.getDepartureMonth(), searchFlightRequest.getDepartureYear(),
				searchFlightRequest.getArrivalHour(), searchFlightRequest.getArrivalDay(), searchFlightRequest.getArrivalMonth(), searchFlightRequest.getArrivalYear(), searchFlightRequest.getSeatType(), searchFlightRequest.getSeatNumber()));
		
		return requiredFlights;
	}

	@Override
	@Transactional
	public JSONObject getFlight(int flightId) {
		JSONObject theFlight = flightDAO.getFlight(flightId);

		if(theFlight == null) {
			throw new ResourceNotFoundException();
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
	
	private void checkParamsForGetFlights(SearchFlightRequest searchFlightRequest) {
		if(searchFlightRequest.getSourceAirport() == null && searchFlightRequest.getSourceCity() == null && searchFlightRequest.getSourceCountry() == null && searchFlightRequest.getDestinationAirport() == null &&
				searchFlightRequest.getDestinationCity() == null && searchFlightRequest.getDestinationCountry() == null && searchFlightRequest.getSeatType() == null && searchFlightRequest.getSeatNumber() == 0) {

			throw new ValidateException();
		}
	}
	
	
}
