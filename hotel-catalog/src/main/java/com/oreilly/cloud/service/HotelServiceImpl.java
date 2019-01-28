package com.oreilly.cloud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Hotel;
import com.oreilly.cloud.object.HotelResource;
import com.oreilly.cloud.object.SearchHotelRequest;
import com.oreilly.cloud.repository.HotelRepository;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	HotelRepository hotelRepository;
	

	@Override
	@Transactional
	public HotelResource getHotel(int hotelId) throws ValidateException, ResourceNotFoundException {
		checkGetHotelParam(hotelId);
		
		Optional<Hotel> theHotel = hotelRepository.findById(hotelId);
		
		if(!theHotel.isPresent()) {
			throw new ResourceNotFoundException();
		}
		
		HotelResource hotelResource = com.oreilly.cloud.service.Converter.convertInHotelResource(theHotel.get());
		
		return hotelResource;
	}
	
	@Override
	@Transactional
	public List<HotelResource> getHotels(SearchHotelRequest searchHotelRequest) {
		checkGetHotelsParams(searchHotelRequest);
		
		List<HotelResource> foundedHotels;
		
		
		
		
		return null;
	}
	
	/*
	 

		List<FlightResource> theRequiredFlights = new ArrayList<>();
		List<Flight> foundedFlights = getFlightsByClass(searchFlightRequest);
		
		if(foundedFlights == null) {
			throw new ResourceNotFoundException();
		}
		for(Flight theFlight: foundedFlights) {
			theRequiredFlights.add(com.oreilly.cloud.service.Converter.convertInFlightResource(theFlight));
		}
	 */
	
	
	
	
	private void checkGetHotelParam(int hotelId) {
		if(hotelId <= 0) {
			throw new ValidateException();
		}
	}
	
	private void checkGetHotelsParams(SearchHotelRequest searchHotelRequest) {
		if(searchHotelRequest.getCity() == null || searchHotelRequest.getCheckIn() == null || searchHotelRequest.getCheckOut() == null) {
			throw new ValidateException();
		}
		
		if(searchHotelRequest.getHostsNumber() <= 0) {
			searchHotelRequest.setHostsNumber(1);
		}
	}
	

}
