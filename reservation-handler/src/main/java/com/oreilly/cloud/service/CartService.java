package com.oreilly.cloud.service;

import java.util.List;

import com.oreilly.cloud.object.BankDetails;
import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.object.HotelReservationResource;

public interface CartService {
	
	public void addElementToCart(FlightReservationResource element, int userId);
	
	public void addElementToCart(HotelReservationResource element, int userId);
	
	public void checkIsInUserCart(int userId, int reservationId, String elementType);
	
	public List<Integer> getUserElementsInCart(int userId, String elementType);
	
	public void deleteElementFromCart(int reservationId, String elementType);
	
	public void checkBankDetails(BankDetails bankDetails);
	
	public void confirmReservationInCart(int userId, int reservationId, String elementType);
	
}
