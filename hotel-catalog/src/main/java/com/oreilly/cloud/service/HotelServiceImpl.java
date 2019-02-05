package com.oreilly.cloud.service;

import static com.oreilly.cloud.predicate.HotelPredicates.getSearchHotelPredicate;
import static com.oreilly.cloud.service.Converter.convertInHotelResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Hotel;
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.HotelResource;
import com.oreilly.cloud.object.SearchHotelRequest;
import com.oreilly.cloud.repository.HotelRepository;
import com.querydsl.core.types.Predicate;

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
	public List<HotelResource> getHotels(SearchHotelRequest searchHotelRequest) throws ResourceNotFoundException, ValidateException {
		checkGetHotelsParams(searchHotelRequest);
		
		List<HotelResource> theRequiredHotels = new ArrayList<>();
		List<Hotel> foundHotels;
		
		Predicate thePredicate = getSearchHotelPredicate(searchHotelRequest.getHotelProperties(), searchHotelRequest.getCity());
		Iterable<Hotel> hotelIterator = hotelRepository.findAll(thePredicate);
		foundHotels = Lists.newArrayList(hotelIterator);
		
		if(foundHotels.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for(Hotel theHotel: foundHotels) {
			theRequiredHotels.add(convertInHotelResource(theHotel));
		}
		
		return theRequiredHotels;
	}
	
	
	private void checkGetHotelParam(int hotelId) {
		if(hotelId <= 0) {
			throw new ValidateException();
		}
	}
	
	private void checkGetHotelsParams(SearchHotelRequest searchHotelRequest) {
		if(searchHotelRequest == null) {
			throw new ValidateException();
		}
		checkCity(searchHotelRequest.getCity());
		checkCheckTime(searchHotelRequest.getCheckIn());
		checkCheckTime(searchHotelRequest.getCheckOut());
		if(searchHotelRequest.getHostsNumber() <= 0) {
			searchHotelRequest.setHostsNumber(1);
		}
	}
	
	private void checkCity(String city) {
		if(city == null) {
			throw new ValidateException();
		} else if(city.equals("")) {
			throw new ValidateException();
		}
	}
	
	private void checkCheckTime(CheckTime checkTime) {
		if(checkTime == null) {
			throw new ValidateException();
		} else if(checkTime.getDay() < 1 || checkTime.getDay() > 31 ||
				checkTime.getMonth() < 1 || checkTime.getMonth() > 12 ||
				checkTime.getYear() < 2019) {
			
			throw new ValidateException();
		}
	}
	

}
