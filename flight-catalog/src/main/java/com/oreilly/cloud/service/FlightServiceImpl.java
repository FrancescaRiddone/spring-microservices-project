package com.oreilly.cloud.service;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.SearchFlightRequest;
import com.oreilly.cloud.repository.FlightRepository;

import java.util.ArrayList;
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

		List<FlightResource> theRequiredFlights = new ArrayList<>();
		List<Flight> foundedFlights = getFlightsByClass(searchFlightRequest);
		
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
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber) throws ValidateException {
		if(flightId <= 0 || seatClass == null || 
				(!seatClass.equals("economy") && !seatClass.equals("business") && !seatClass.equals("first")) || 
				seatNumber < 0) {
			throw new ValidateException();
		}
		Flight theFlight = checkFlightAvailabilityByClass(flightId, seatClass, seatNumber);
		
		return theFlight;
	}
	
	@Override
	@Transactional
	public void updateAvailableSeats(int flightId, String seatClass, int seatNumber) throws ValidateException {
		checkParamsForUpdateAvailableSeats(flightId, seatClass, seatNumber);
		
		updateAvailableSeatsBySeatClass(flightId, seatClass, seatNumber);
	}
	
	
	private void checkGetFlightParam(int flightId) {
		if(flightId <= 0) {
			throw new ValidateException();
		}
	}
	
	protected List<Flight> getFlightsByClass(SearchFlightRequest searchFlightRequest){
		List<Flight> foundFlights;
		
		if(searchFlightRequest.getSeatNumber() == 0) {
			searchFlightRequest.setSeatNumber(1);
		}
		
		if(searchFlightRequest.getSeatType().equals("economy")) {
			foundFlights = flightRepository.findBySourceDestinationSeatNumberAndEconomyAvailability(
					searchFlightRequest.getSource().getAirportName(), 
					searchFlightRequest.getSource().getCity(), 
					searchFlightRequest.getSource().getCountry(), 
					searchFlightRequest.getDestination().getAirportName(), 
					searchFlightRequest.getDestination().getCity(), 
					searchFlightRequest.getDestination().getCountry(), 
					searchFlightRequest.getSeatNumber());
		} else if(searchFlightRequest.getSeatType().equals("business")) {
			foundFlights = flightRepository.findBySourceDestinationSeatNumberAndBusinessAvailability(
					searchFlightRequest.getSource().getAirportName(), 
					searchFlightRequest.getSource().getCity(), 
					searchFlightRequest.getSource().getCountry(), 
					searchFlightRequest.getDestination().getAirportName(), 
					searchFlightRequest.getDestination().getCity(), 
					searchFlightRequest.getDestination().getCountry(), 
					searchFlightRequest.getSeatNumber());
		} else if(searchFlightRequest.getSeatType().equals("first")) {
			foundFlights = flightRepository.findBySourceDestinationSeatNumberAndFirstAvailability(
					searchFlightRequest.getSource().getAirportName(), 
					searchFlightRequest.getSource().getCity(), 
					searchFlightRequest.getSource().getCountry(), 
					searchFlightRequest.getDestination().getAirportName(), 
					searchFlightRequest.getDestination().getCity(), 
					searchFlightRequest.getDestination().getCountry(), 
					searchFlightRequest.getSeatNumber());
		} else {
			foundFlights = flightRepository.findBySourceDestinationSeatNumberAndAvailability(
					searchFlightRequest.getSource().getAirportName(), 
					searchFlightRequest.getSource().getCity(), 
					searchFlightRequest.getSource().getCountry(), 
					searchFlightRequest.getDestination().getAirportName(), 
					searchFlightRequest.getDestination().getCity(), 
					searchFlightRequest.getDestination().getCountry(),
					searchFlightRequest.getSeatNumber());
		}
		
		return foundFlights;
	}

	protected Flight checkFlightAvailabilityByClass(int flightId, String seatClass, int seatNumber) {
		Flight theCheckedFlight;
		if(seatClass.equals("economy")) {
			theCheckedFlight = flightRepository.findByIdSeatNumberAndEconomyAvailability(flightId, seatNumber);
		} else if(seatClass.equals("business")) {
			theCheckedFlight = flightRepository.findByIdSeatNumberAndBusinessAvailability(flightId, seatNumber);
		} else {
			theCheckedFlight = flightRepository.findByIdSeatNumberAndFirstAvailability(flightId, seatNumber);
		}
		
		return theCheckedFlight;
	}

	private void checkParamsForGetFlights(SearchFlightRequest searchFlightRequest) throws ValidateException {
		if((searchFlightRequest.getSource().getAirportName() == null || searchFlightRequest.getSource().getAirportName().equals("")) && 
			(searchFlightRequest.getSource().getCity() == null || searchFlightRequest.getSource().getCity().equals("")) && 
			(searchFlightRequest.getSource().getCountry() == null || searchFlightRequest.getSource().getCountry().equals("")) && 
			(searchFlightRequest.getDestination().getAirportName() == null || searchFlightRequest.getDestination().getAirportName().equals("") ) &&
			(searchFlightRequest.getDestination().getCity() == null || searchFlightRequest.getDestination().getCity().equals("")) && 
			(searchFlightRequest.getDestination().getCountry() == null || searchFlightRequest.getDestination().getCountry().equals("")) && 
			(searchFlightRequest.getSeatType() == null || !(searchFlightRequest.getSeatType().equals("economy") || 
				searchFlightRequest.getSeatType().equals("business") || searchFlightRequest.getSeatType().equals("first")) ||
				searchFlightRequest.getSeatType().equals("")) && 
			searchFlightRequest.getSeatNumber() == 0) {

			throw new ValidateException();
		}
	}
	
	private void checkParamsForUpdateAvailableSeats(int flightId, String seatClass, int seatNumber) throws ValidateException {
		if(flightId <= 0 || seatClass == null || seatClass.equals("") || seatNumber == 0 || 
				!(seatClass.equals("economy") || seatClass.equals("business") || seatClass.equals("first"))) {
			throw new ValidateException();
		}
	}
	
	protected void updateAvailableSeatsBySeatClass(int flightId, String seatClass, int seatNumber) {
		if(seatClass.equals("economy")) {
			flightRepository.updateAvailableEconomySeats(flightId, seatNumber);
		} else if(seatClass.equals("business")) {
			flightRepository.updateAvailableBusinessSeats(flightId, seatNumber);
		} else {
			flightRepository.updateAvailableFirstSeats(flightId, seatNumber);
		}
	}
	
	
}
