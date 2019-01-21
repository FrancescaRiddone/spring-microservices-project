package com.oreilly.cloud.dao;

import com.oreilly.cloud.entity.Reservation;

public interface ReservationDAO {
	
	public Reservation getReservation(int reservationId);
	
	public Reservation saveReservation(Reservation theReservation);

}
