package com.oreilly.cloud.service;

import static com.oreilly.cloud.service.Converter.convertInReservationResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.model.ReservationSeat;
import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.repository.ReservationRepository;
import com.oreilly.cloud.repository.ReservationSeatRepository;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private ReservationSeatRepository reservationSeatRepository;
	

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
		
		Reservation theSavedReservation;
		
		if(theReservation.getReservationSeats() == null || theReservation.getReservationSeats().isEmpty()) {
			Reservation startReservation = reservationRepository.save(theReservation);
			
			Reservation reservationWithSeats = setSeatsInReservation(startReservation);
			theSavedReservation = reservationRepository.save(reservationWithSeats);	
		} else {
			theSavedReservation = reservationRepository.save(theReservation);
		}
		
		FlightReservationResource reservationResource = convertInReservationResource(theSavedReservation);
		
		return reservationResource;
	}
	
	@Override
	@Transactional
	public void deleteReservation(int reservationId) throws ResourceNotFoundException, ValidateException {
		reservationRepository.deleteById(reservationId);
	}
	
	
	private void checkParamForSaveReservation(Reservation theReservation) throws ValidateException {
		if(theReservation == null) {
			throw new ValidateException();
		}
		if((theReservation.getFlight() == null || theReservation.getFlight().getFlightId() <= 0) || 
				(theReservation.getUserEmail() == null || theReservation.getUserEmail().equals("")) ||
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
	
	private Reservation setSeatsInReservation(Reservation theReservation) {
		char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
		List<String> seats = new ArrayList<>();
		List<ReservationSeat> reservationSeats = new ArrayList<>();
		
		for(int i = 0; i < theReservation.getSeatsNumber(); i++) {
			String seat = null;
			do {
				int randomForNumber = randInt(1, 20);
				int randomForLetter = randInt(0, 7);
				seat = randomForNumber + "" +  letters[randomForLetter] + "";
				
			} while(seat == null || seat.isEmpty() || seats.contains(seat));
			
			seats.add(seat);
			
			ReservationSeat reservationSeat = new ReservationSeat(seat, theReservation);
			ReservationSeat savedReservationSeat = reservationSeatRepository.save(reservationSeat);
			
			reservationSeats.add(savedReservationSeat);
		}
		
		theReservation.setReservationSeats(reservationSeats);
		
		return theReservation;
	}
	
	private static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}


}
