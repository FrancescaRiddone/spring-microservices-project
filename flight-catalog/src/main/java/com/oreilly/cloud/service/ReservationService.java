package com.oreilly.cloud.service;

import com.oreilly.cloud.entity.Reservation;
import com.oreilly.cloud.object.ReservationResource;

public interface ReservationService {
	
	public Reservation getReservation(int reservationId);
	
	public ReservationResource getReservationResource(int reservationId);
	
	public ReservationResource saveReservation(Reservation theReservation);

}
