package com.microservices.project.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.microservices.project.exception.ResourceNotFoundException;
import com.microservices.project.exception.ResourceUnavailableException;
import com.microservices.project.exception.ValidateException;
import com.microservices.project.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.project.model.Reservation;
import com.microservices.project.model.Room;
import com.microservices.project.object.CheckTime;
import com.microservices.project.object.HotelReservationResource;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	@Transactional
	public List<Room> getReservedRooms(CheckTime checkIn, CheckTime checkOut, List<Integer> roomIds) throws ValidateException {
		checkGetReservedRoomsParams(checkIn, checkOut, roomIds);
		
		LocalDateTime startDate = convertInLocalDateTime(checkIn);
		LocalDateTime endDate = convertInLocalDateTimeMinusOne(checkOut);
		
		List<Room> reservedRooms = reservationRepository.findReservedRooms(startDate, endDate, roomIds);
		
		return reservedRooms;
	}
	
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
	public HotelReservationResource getReservationResource(int reservationId) throws ResourceNotFoundException {
		Reservation theReservation = getReservation(reservationId);
		
		HotelReservationResource reservationResource = Converter.convertInReservationResource(theReservation);
		
		return reservationResource;
	}
	
	@Override
	@Transactional
	public void checkRoomAvailability(int roomId, int hostsNumber, CheckTime checkIn, CheckTime checkOut) throws ValidateException, ResourceUnavailableException {
		checkRoomAvailabilityParams(roomId, checkIn, checkOut, hostsNumber);
		
		List<Integer> roomIds = new ArrayList<>();
		roomIds.add(roomId);
		List<Room> reservedRoom = getReservedRooms(checkIn, checkOut, roomIds);
		if(!reservedRoom.isEmpty()) {
			throw new ResourceUnavailableException();
		}
	}
	
	@Override
	@Transactional
	public HotelReservationResource saveReservation(Reservation theReservation) throws ValidateException {
		checkParamForSaveReservation(theReservation);
		
		theReservation.setPrice(getPriceFotReservationTypeAndNigths(theReservation));
		Reservation theSavedReservation = reservationRepository.save(theReservation);
		HotelReservationResource reservationResource = Converter.convertInReservationResource(theSavedReservation);
		
		return reservationResource;
	}
	
	@Override
	@Transactional
	public void deleteReservation(int reservationId) throws ResourceNotFoundException, ValidateException {
		getReservation(reservationId);
		
		reservationRepository.deleteById(reservationId);
	}
	
	@Override
	@Transactional
	public void confirmReservation(int reservationId) throws ValidateException {
		if(reservationId <= 0) {
			throw new ValidateException();
		}
		
		reservationRepository.updateReservationConfirmation(reservationId);
	}
	

	private void checkGetReservedRoomsParams(CheckTime checkIn, CheckTime checkOut, List<Integer> roomIds) throws ValidateException {
		if(roomIds == null) {
			throw new ValidateException();
		}
		checkDates(checkIn, checkOut);	
	}
	
	private void checkDates(CheckTime checkIn, CheckTime checkOut) {
		if(checkIn == null || checkOut == null) {
			throw new ValidateException();
		}
		if(checkIn.getDay() < 1 || checkIn.getDay() > 31 || checkIn.getMonth() < 1 || checkIn.getMonth() > 12 || checkIn.getYear() < 2019 ||
				checkOut.getDay() < 1 || checkOut.getDay() > 31 || checkOut.getMonth() < 1 || checkOut.getMonth() > 12 || checkOut.getYear() < 2019) {
			throw new ValidateException();
		}
		
		LocalDateTime startDate =  convertInLocalDateTime(checkIn);
		LocalDateTime endDate =  convertInLocalDateTime(checkOut);
		if(!startDate.isBefore(endDate)) {
			throw new ValidateException();
		}	
	}
	
	private LocalDateTime convertInLocalDateTime(CheckTime theTime) {
		LocalDateTime dateTime = LocalDateTime.of(theTime.getYear(), theTime.getMonth(), theTime.getDay(), 0, 0);
		
		return dateTime;
	}
	
	private LocalDateTime convertInLocalDateTimeMinusOne(CheckTime theTime) {
		LocalDateTime dateTime = LocalDateTime.of(theTime.getYear(), theTime.getMonth(), theTime.getDay(), 0, 0);
		dateTime = dateTime.minusDays(1);
		
		return dateTime;
	}
	
	private void checkRoomAvailabilityParams(int roomId, CheckTime checkIn, CheckTime checkOut, int hostsNumber) throws ValidateException {
		if(roomId <= 0 || hostsNumber <= 0 || checkIn == null || checkOut == null) {
			throw new ValidateException();
		}
		checkDates(checkIn, checkOut);
	}
	
	private void checkParamForSaveReservation(Reservation theReservation) throws ValidateException {
		if(theReservation == null) {
			throw new ValidateException();
		}
		if((theReservation.getRoom() == null || theReservation.getRoom().getId() <= 0) || 
				(theReservation.getUserEmail() == null || theReservation.getUserEmail().equals("")) ||
				!(theReservation.getReservationType().equals("standard") || theReservation.getReservationType().equals("with breakfast") || 
						theReservation.getReservationType().equals("half board") || theReservation.getReservationType().equals("full board")) ||
				theReservation.getHostsNumber() <= 0 ||
				theReservation.getCheckIn() == null || theReservation.getCheckOut() == null) {
			
			throw new ValidateException();
		}
		if(!theReservation.getCheckIn().isBefore(theReservation.getCheckOut())) {
			throw new ValidateException();
		}
		if(theReservation.getRoom().getHostsNumber() != theReservation.getHostsNumber()) {
			throw new ValidateException();
		}
	}
	
	private double getPriceFotReservationTypeAndNigths(Reservation theReservation) {
		double totalPrice;
		double priceForNight;
        int nightsNumber = (int) ChronoUnit.DAYS.between(theReservation.getCheckIn(), theReservation.getCheckOut());
		
        if(theReservation.getReservationType().equals("with breakfast")) {
        	priceForNight = theReservation.getRoom().getWithBreakfastDailyPrice();
		} else if(theReservation.getReservationType().equals("half board")) {
			priceForNight = theReservation.getRoom().getHalfBoardDailyPrice();
		} else if(theReservation.getReservationType().equals("full board")) {
			priceForNight = theReservation.getRoom().getFullBoardDailyPrice();
		} else {
			priceForNight = theReservation.getRoom().getStandardDailyPrice();
		}
        totalPrice = nightsNumber * priceForNight;
		
		return totalPrice;
	}
	
	
}
