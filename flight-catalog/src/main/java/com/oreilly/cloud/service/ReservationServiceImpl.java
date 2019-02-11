package com.oreilly.cloud.service;

import static com.oreilly.cloud.service.Converter.convertInReservationResource;

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
	public Reservation getReservation(int reservationId) throws ResourceNotFoundException, ValidateException {
		if(reservationId <= 0) {
			throw new ValidateException();
		}
		Optional<Reservation> theReservation = reservationRepository.findById(reservationId);
		if(!theReservation.isPresent()) {
			throw new ResourceNotFoundException();
		}
		
		return theReservation.get();
	}
	
	@Override
	@Transactional
	public FlightReservationResource getReservationResource(int reservationId) throws ResourceNotFoundException, ValidateException {
		Reservation theReservation = getReservation(reservationId);
		
		FlightReservationResource reservationResource = convertInReservationResource(theReservation);
		
		return reservationResource;
	}
	
	@Override
	@Transactional
	public FlightReservationResource saveReservation(Reservation theReservation) throws ValidateException {
		checkParamForSaveReservation(theReservation);
		
		theReservation.setPrice(getPriceForClassAndSeatNumber(theReservation.getFlight(), theReservation.getSeatsType(), 
				theReservation.getSeatsNumber()));
		
		Reservation theSavedReservation = reservationRepository.save(theReservation);
		
		FlightReservationResource reservationResource = convertInReservationResource(theSavedReservation);
		
		return reservationResource;
	}
	
	
	private void checkParamForSaveReservation(Reservation theReservation) throws ValidateException {
		if(theReservation == null) {
			throw new ValidateException();
		}
		if((theReservation.getFlight() == null || theReservation.getFlight().getFlightId() <= 0) || 
				(theReservation.getUserName() == null || theReservation.getUserName().equals("")) ||
				(theReservation.getUserSurname() == null || theReservation.getUserSurname().equals("")) ||
				!(theReservation.getSeatsType().equals("economy") || theReservation.getSeatsType().equals("business") || 
						theReservation.getSeatsType().equals("first")) ||
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
