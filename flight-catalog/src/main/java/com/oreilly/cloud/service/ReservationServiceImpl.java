package com.oreilly.cloud.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.dao.ReservationDAO;
import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.entity.Reservation;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationDAO reservationDAO;

	@Override
	@Transactional
	public JSONObject getReservationJSON(int reservationId) {
		if(reservationId <= 0) {
			return new JSONObject();
		}
		
		JSONObject theReservation = reservationDAO.getReservationJSON(reservationId);
		
		if(theReservation == null) {
			theReservation = new JSONObject();
		}
		
		return theReservation;
	}
	
	@Override
	@Transactional
	public Reservation getReservation(int reservationId) {
		if(reservationId <= 0) {
			return null;
		}
		
		Reservation theReservation =  reservationDAO.getReservation(reservationId);
		
		return theReservation;
	}

	@Override
	@Transactional
	public JSONObject saveReservation(Flight flight, String userName, String userSurname, String seatClass, int seatNumber, boolean confirmed) {
		if(!checkParamsForSaveReservation(flight, userName, userSurname, seatClass, seatNumber)) {
			return new JSONObject();
		}
		
		JSONObject theSavedReservation = reservationDAO.saveReservation(flight, userName, userSurname, seatClass, seatNumber, confirmed);
		
		if(theSavedReservation == null) {
			theSavedReservation = new JSONObject();
		}
		
		return theSavedReservation;
	}
	
	private boolean checkParamsForSaveReservation(Flight flight, String userName, String userSurname, String seatClass, int seatNumber) {
		if(flight != null && flight.getFlightId() > 0 && 
				userName != null && !userName.equals("") &&
				userSurname != null && !userSurname.equals("") && 
				(seatClass.equals("economy") || seatClass.equals("business") || seatClass.equals("first")) &&
				seatNumber > 0) {
			
			return true;
		}
		return false;
	}


}
