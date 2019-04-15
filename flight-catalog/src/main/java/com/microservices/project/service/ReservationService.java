package com.microservices.project.service;

import com.microservices.project.model.Reservation;
import com.microservices.project.object.FlightReservationResource;

public interface ReservationService {
	
	public Reservation getReservation(int reservationId);
	
	public FlightReservationResource getReservationResource(int reservationId);
	
	public FlightReservationResource saveReservation(Reservation theReservation);
	
	public void deleteReservation(int reservationId);
	
	public void confirmReservation(int reservationId);

}
