package com.oreilly.cloud.service;

import static com.oreilly.cloud.service.Converter.convertInReservationResource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ResourceUnavailableException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.model.Room;
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public List<Room> getReservedRooms(CheckTime checkIn, CheckTime checkOut, List<Integer> roomIds) {
		checkGetReservedRoomsParams(checkIn, checkOut, roomIds);
		
		LocalDateTime startDate = convertInLocalDateTime(checkIn);
		LocalDateTime endDate = convertInLocalDateTime(checkOut);
		
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
		
		HotelReservationResource reservationResource = convertInReservationResource(theReservation);
		
		return reservationResource;
	}
	
	@Override
	public void checkRoomAvailability(int roomId, int hostsNumber, CheckTime checkIn, CheckTime checkOut) {
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
		HotelReservationResource reservationResource = convertInReservationResource(theSavedReservation);
		
		return reservationResource;
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
				(theReservation.getUserName() == null || theReservation.getUserName().equals("")) ||
				(theReservation.getUserSurname() == null || theReservation.getUserSurname().equals("")) ||
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
