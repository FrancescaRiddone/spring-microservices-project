package com.oreilly.cloud.service;

import com.google.common.collect.Lists;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.FlightTime;
import com.oreilly.cloud.object.JourneyStage;
import com.oreilly.cloud.object.SearchFlightRequest;
import com.oreilly.cloud.repository.FlightRepository;
import com.querydsl.core.types.Predicate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightServiceImpl implements FlightService{
	
	@Autowired
	FlightRepository flightRepository;
	
	
	@Override
	@Transactional
	public FlightResource getFlight(int flightId) throws ValidateException, ResourceNotFoundException {
		checkGetFlightParam(flightId);
		
		Optional<Flight> theFlight = flightRepository.findById(flightId);
		if(!theFlight.isPresent()) {
			throw new ResourceNotFoundException();
		}
		
		FlightResource flightResource = com.oreilly.cloud.service.Converter.convertInFlightResource(theFlight.get());
		
		return flightResource;
	}

	@Override
	@Transactional
	public List<FlightResource> getFlights(SearchFlightRequest searchFlightRequest) throws ResourceNotFoundException, ValidateException {
		checkParamsForGetFlights(searchFlightRequest);
		if(searchFlightRequest.getSeatNumber() == 0) {
			searchFlightRequest.setSeatNumber(1);
		}
		
		List<FlightResource> theRequiredFlights = new ArrayList<>();
		List<Flight> foundFlights = null;
		
		Predicate thePredicate = com.oreilly.cloud.predicate.FlightPredicates.getSearchFlightsPredicate(searchFlightRequest);
		Iterable<Flight> flightsIterator = flightRepository.findAll(thePredicate);
		foundFlights = Lists.newArrayList(flightsIterator);
		
		if(foundFlights.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for(Flight theFlight: foundFlights) {
			theRequiredFlights.add(com.oreilly.cloud.service.Converter.convertInFlightResource(theFlight));
		}
		
		return theRequiredFlights;
	}
	
	@Override
	@Transactional
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber) throws ValidateException {
		checkFlightAvailabilityParams(flightId, seatClass, seatNumber);
		
		Predicate thePredicate = com.oreilly.cloud.predicate.FlightPredicates.getCheckFlightAvailabilityPredicate(flightId, seatClass, seatNumber);
		Optional<Flight> theFlight = flightRepository.findOne(thePredicate);
		
		return theFlight.get();
	}
	
	@Override
	@Transactional
	public void updateAvailableSeats(int flightId, String seatClass, int seatNumber) throws ValidateException {
		checkParamsForUpdateAvailableSeats(flightId, seatClass, seatNumber);
		
		if(flightRepository.existsById(flightId)) {
			if(seatClass.equals("economy")) {
				flightRepository.updateAvailableEconomySeats(flightId, seatNumber);
			} else if(seatClass.equals("business")) {
				flightRepository.updateAvailableBusinessSeats(flightId, seatNumber);
			} else {
				flightRepository.updateAvailableFirstSeats(flightId, seatNumber);
			}
		}
	}
	
	
	private void checkGetFlightParam(int flightId) {
		if(flightId <= 0) {
			throw new ValidateException();
		}
	}

	private void checkParamsForGetFlights(SearchFlightRequest searchFlightRequest) throws ValidateException {
		if(!checkJourneyStage(searchFlightRequest.getSource()) && !checkJourneyStage(searchFlightRequest.getDestination())) {
			throw new ValidateException();
		}
		
		checkSeatType(searchFlightRequest.getSeatType());
		checkFlightRequestDates(searchFlightRequest);
	}
	
	private boolean checkJourneyStage(JourneyStage theJourneyStage) {
		boolean[] checked = new boolean[4];
		
		if(theJourneyStage == null) {
			return false;
		}
		
		if(theJourneyStage.getAirportName() != null) {
			if(!theJourneyStage.getAirportName().equals("")) {
				checked[0] = true;
			}
		}
		if(theJourneyStage.getCity() != null) {
			if(!theJourneyStage.getCity().equals("")) {
				checked[1] = true;
			}
		}
		if(theJourneyStage.getCountry() != null) {
			if(!theJourneyStage.getCountry().equals("")) {
				checked[2] = true;
			}
		}
		if(theJourneyStage.getAirportCode() != null) {
			if(!theJourneyStage.getAirportCode().equals("")) {
				checked[3] = true;
			}
		}
		
		/*
		boolean checked = (theJourneyStage.getAirportName() != null && !theJourneyStage.getAirportName().equals("")) ||
				(theJourneyStage.getCity() != null && !theJourneyStage.getCity().equals("")) ||
				(theJourneyStage.getCountry() != null && !theJourneyStage.getCountry().equals("")) ||
				(theJourneyStage.getAirportCode() != null && !theJourneyStage.getAirportCode().equals(""));
		*/
				
		
		return (checked[0] || checked[1] || checked[2] || checked[3]);	
	}
	
	private void checkSeatType(String seatType) {
		if(seatType != null && !(seatType.equals("") || seatType.equals("economy") || seatType.equals("business") || seatType.equals("first"))) {
			throw new ValidateException();
		}
	}
	
	private void checkFlightRequestDates(SearchFlightRequest searchFlightRequest) throws ValidateException {
		if(searchFlightRequest.getDepartureTime() == null && searchFlightRequest.getArrivalTime() == null) {
			throw new ValidateException();
		} 
		
		if(searchFlightRequest.getDepartureTime() != null) {
			if(searchFlightRequest.getDepartureTime().getYear() < 2019 ||
					searchFlightRequest.getDepartureTime().getMonth() < 1 || searchFlightRequest.getDepartureTime().getMonth() > 12 ||
					searchFlightRequest.getDepartureTime().getDay() < 1 || searchFlightRequest.getDepartureTime().getDay() > 31 ||
					searchFlightRequest.getDepartureTime().getHour() < 0 || searchFlightRequest.getDepartureTime().getHour() > 23 ||
					searchFlightRequest.getDepartureTime().getMinute() < 0 || searchFlightRequest.getDepartureTime().getMinute() > 59) {
				
				throw new ValidateException();
			}
		} else if(searchFlightRequest.getArrivalTime() != null){
			if(searchFlightRequest.getArrivalTime().getYear() < 2019 ||
					searchFlightRequest.getArrivalTime().getMonth() < 1 || searchFlightRequest.getArrivalTime().getMonth() > 12 ||
					searchFlightRequest.getArrivalTime().getDay() < 1 || searchFlightRequest.getArrivalTime().getDay() > 31 ||
					searchFlightRequest.getArrivalTime().getHour() < 0 || searchFlightRequest.getArrivalTime().getHour() > 23 ||
					searchFlightRequest.getArrivalTime().getMinute() < 0 || searchFlightRequest.getArrivalTime().getMinute() > 59) {
				
				throw new ValidateException();
			}
		}	
	}
	
	private void checkFlightAvailabilityParams(int flightId, String seatClass, int seatNumber) throws ValidateException {
		if(flightId <= 0 || seatClass == null || 
				(!seatClass.equals("economy") && !seatClass.equals("business") && !seatClass.equals("first")) || 
				seatNumber < 0) {
			throw new ValidateException();
		}
	}
	
	private void checkParamsForUpdateAvailableSeats(int flightId, String seatClass, int seatNumber) throws ValidateException {
		if(flightId <= 0 || seatClass == null || seatClass.equals("") || seatNumber == 0 || 
				!(seatClass.equals("economy") || seatClass.equals("business") || seatClass.equals("first")) ) {
			throw new ValidateException();
		}
	}

	
	
}
