package com.oreilly.cloud.dao;

import org.json.simple.JSONObject;

import com.oreilly.cloud.entity.Flight;

public interface ReservationDAO {
	
	public JSONObject getReservation(int reservationId);
	
	public JSONObject saveReservation(Flight flight, String userName, String userSurname, String seatClass, int seatNumber);

}
