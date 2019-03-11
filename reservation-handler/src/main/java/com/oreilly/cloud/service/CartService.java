package com.oreilly.cloud.service;

import java.util.List;

import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.object.HotelReservationResource;

public interface CartService {
	
	public void addElementToCart(FlightReservationResource element, int userId);
	
	public void addElementToCart(HotelReservationResource element, int userId);
	
	public List<Integer> getUserFlightsInCart(int userId);
	
	public List<Integer> getUserHotelsInCart(int userId);
	
	public void deleteElementFromCart(int reservationId, String elementType);
	
}
