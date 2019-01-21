package com.oreilly.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.dao.ReservationDAO;
import com.oreilly.cloud.entity.Reservation;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.object.ReservationResource;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationDAO reservationDAO;

	@Override
	@Transactional
	public Reservation getReservation(int reservationId) {
		if(reservationId <= 0) {
			return null;
		}
		
		Reservation theReservation =  reservationDAO.getReservation(reservationId);
		if(theReservation == null) {
			throw new ResourceNotFoundException();
		}
		
		return theReservation;
	}
	
	@Override
	@Transactional
	public ReservationResource getReservationResource(int reservationId) {
		if(reservationId <= 0) {
			return null;
		}
		
		Reservation theReservation =  reservationDAO.getReservation(reservationId);
		if(theReservation == null) {
			throw new ResourceNotFoundException();
		}
		ReservationResource reservationResource = com.oreilly.cloud.service.Converter.convertInReservationResource(theReservation);
		
		return reservationResource;
	}
	
	@Override
	@Transactional
	public ReservationResource saveReservation(Reservation theReservation) {
		checkParamsForSaveReservation(theReservation);
		
		Reservation theSavedReservation = reservationDAO.saveReservation(theReservation);
		
		if(theSavedReservation == null) {
			throw new ResourceNotFoundException();
		}
		ReservationResource reservationResource = com.oreilly.cloud.service.Converter.convertInReservationResource(theSavedReservation);
		
		return reservationResource;
	}
	
	private void checkParamsForSaveReservation(Reservation theReservation) {
		if((theReservation.getFlight() == null || theReservation.getFlight().getId() <= 0) && 
				(theReservation.getUserName() == null || theReservation.getUserName().equals("")) &&
				(theReservation.getUserSurname() == null || theReservation.getUserSurname().equals("")) && 
				!(theReservation.getSeatsType().equals("economy") || theReservation.getSeatsType().equals("business") || 
						theReservation.getSeatsType().equals("first")) &&
				theReservation.getSeatsNumber() <= 0) {
			
			throw new ValidateException();
		}
	}


}
