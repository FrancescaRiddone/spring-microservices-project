package com.oreilly.cloud.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.dao.HotelDAO;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	private HotelDAO hotelDAO;
	
	
	
	
	/*
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
	*/

}
