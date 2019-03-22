package com.oreilly.cloud.service;

import java.util.List;

import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.model.Room;
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.HotelReservationResource;

public interface ReservationService {
	
	public List<Room> getReservedRooms(CheckTime checkIn, CheckTime checkOut, List<Integer> roomIds);
	
	public Reservation getReservation(int reservationId);
	
	public HotelReservationResource getReservationResource(int reservationId);
	
	public void checkRoomAvailability(int roomId, int HostsNumber, CheckTime checkIn, CheckTime checkOut);
	
	public HotelReservationResource saveReservation(Reservation theReservation);
	
	public void deleteReservation(int reservationId);
	
	public void confirmReservation(int reservationId);

}
