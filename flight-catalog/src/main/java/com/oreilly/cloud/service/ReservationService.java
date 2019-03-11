package com.oreilly.cloud.service;

import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.object.FlightReservationResource;

public interface ReservationService {
	
	public Reservation getReservation(int reservationId);
	
	public FlightReservationResource getReservationResource(int reservationId);
	
	public FlightReservationResource saveReservation(Reservation theReservation);
	
	public void deleteReservation(int reservationId);

}
