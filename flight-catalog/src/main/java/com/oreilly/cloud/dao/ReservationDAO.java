package com.oreilly.cloud.dao;

import org.json.simple.JSONObject;

import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.entity.Reservation;

public interface ReservationDAO {
	
	public JSONObject getReservationJSON(int reservationId);
	
	public Reservation getReservation(int reservationId);
	
	public JSONObject saveReservation(Flight flight, String userName, String userSurname, String seatClass, int seatNumber, boolean confirmed);

}
