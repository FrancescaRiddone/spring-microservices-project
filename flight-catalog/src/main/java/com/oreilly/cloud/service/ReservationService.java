package com.oreilly.cloud.service;

import org.json.simple.JSONObject;

import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.entity.Reservation;

public interface ReservationService {
	
	public JSONObject getReservationJSON(int reservationId);
	
	public Reservation getReservation(int reservationId);
	
	public JSONObject saveReservation(Flight flight, String userName, String userSurname, String seatClass, int seatNumber, boolean confirmed);

}
