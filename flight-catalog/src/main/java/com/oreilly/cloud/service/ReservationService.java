package com.oreilly.cloud.service;

import org.json.simple.JSONObject;

import com.oreilly.cloud.entity.Flight;

public interface ReservationService {
	
	public JSONObject getReservation(int reservationId);
	
	public JSONObject saveReservation(Flight flight, String userName, String userSurname, String seatClass, int seatNumber);

}
