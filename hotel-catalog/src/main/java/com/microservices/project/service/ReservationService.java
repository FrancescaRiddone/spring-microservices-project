package com.microservices.project.service;

import java.util.List;

import com.microservices.project.model.Reservation;
import com.microservices.project.model.Room;
import com.microservices.project.object.CheckTime;
import com.microservices.project.object.HotelReservationResource;

public interface ReservationService {
	
	public List<Room> getReservedRooms(CheckTime checkIn, CheckTime checkOut, List<Integer> roomIds);
	
	public Reservation getReservation(int reservationId);
	
	public HotelReservationResource getReservationResource(int reservationId);
	
	public void checkRoomAvailability(int roomId, int HostsNumber, CheckTime checkIn, CheckTime checkOut);
	
	public HotelReservationResource saveReservation(Reservation theReservation);
	
	public void deleteReservation(int reservationId);
	
	public void confirmReservation(int reservationId);

}
