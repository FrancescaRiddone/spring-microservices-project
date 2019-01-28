package com.oreilly.cloud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	ReservationRepository reservationRepository;

	@Override
	@Transactional
	public Reservation getReservation(int reservationId) throws ResourceNotFoundException {
		if(reservationId <= 0) {
			return null;
		}
		Optional<Reservation> theReservation = reservationRepository.findById(reservationId);
		if(!theReservation.isPresent()) {
			throw new ResourceNotFoundException();
		}
		
		return theReservation.get();
	}
	
	@Override
	@Transactional
	public FlightReservationResource getReservationResource(int reservationId) throws ResourceNotFoundException {
		Reservation theReservation = getReservation(reservationId);
		FlightReservationResource reservationResource = com.oreilly.cloud.service.Converter.convertInReservationResource(theReservation);
		
		return reservationResource;
	}
	
	@Override
	@Transactional
	public FlightReservationResource saveReservation(Reservation theReservation) throws ResourceNotFoundException {
		checkParamsForSaveReservation(theReservation);
		
		theReservation.setPrice(getPriceForClassAndSeatNumber(theReservation.getFlight(), theReservation.getSeatsType(), 
				theReservation.getSeatsNumber()));
		
		Reservation theSavedReservation = reservationRepository.save(theReservation);
		if(theSavedReservation == null) {
			throw new ResourceNotFoundException();
		}
		FlightReservationResource reservationResource = com.oreilly.cloud.service.Converter.convertInReservationResource(theSavedReservation);
		
		return reservationResource;
	}
	
	private void checkParamsForSaveReservation(Reservation theReservation) throws ValidateException {
		if((theReservation.getFlight() == null || theReservation.getFlight().getFlightId() <= 0) && 
				(theReservation.getUserName() == null || theReservation.getUserName().equals("")) &&
				(theReservation.getUserSurname() == null || theReservation.getUserSurname().equals("")) && 
				!(theReservation.getSeatsType().equals("economy") || theReservation.getSeatsType().equals("business") || 
						theReservation.getSeatsType().equals("first")) &&
				theReservation.getSeatsNumber() <= 0) {
			
			throw new ValidateException();
		}
	}
	
	private double getPriceForClassAndSeatNumber(Flight flight, String seatClass, int seatNumber) {
		if(seatClass.equals("economy")) {
			return (flight.getEconomySeatPrice() * seatNumber);
		} else if(seatClass.equals("business")) {
			return (flight.getBusinessSeatPrice() * seatNumber);
		}
		return (flight.getFirstSeatPrice() * seatNumber);
	}


}
