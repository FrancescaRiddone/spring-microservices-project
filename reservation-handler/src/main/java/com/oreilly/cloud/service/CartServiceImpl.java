package com.oreilly.cloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.CartElement;
import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepository;

	
	@Override
	@Transactional
	public void addElementToCart(FlightReservationResource element, int userId) throws ValidateException {
		checkAddElementToCart1Params(element, userId);
		CartElement newCartElement = new CartElement(userId, element.getReservationId(), "flight", false);
		
		CartElement savedCartElement = cartRepository.save(newCartElement);
		
		System.out.println("elemento salvato: " + savedCartElement);
		
		for(CartElement ce: cartRepository.findAll()) {
			System.out.println("cart element: " + ce);
		}
	}
	
	@Override
	@Transactional
	public void addElementToCart(HotelReservationResource element, int userId) throws ValidateException {
		checkAddElementToCart2Params(element, userId);
		CartElement newCartElement = new CartElement(userId, element.getReservationId(), "hotel", false);
		
		CartElement savedCartElement = cartRepository.save(newCartElement);
		
		System.out.println("elemento salvato: " + savedCartElement);
		
		for(CartElement ce: cartRepository.findAll()) {
			System.out.println("cart element: " + ce);
		}
	}
	
	@Override
	@Transactional
	public List<Integer> getUserFlightsInCart(int userId) throws ValidateException {
		checkIdInParam(userId);
		
		List<Integer> flightsInCartIds = cartRepository.findElementsInCartByUserIdAndType(userId, "flight");

		return flightsInCartIds;
	}

	@Override
	@Transactional
	public List<Integer> getUserHotelsInCart(int userId) throws ValidateException {
		checkIdInParam(userId);
		
		List<Integer> hotelsInCartIds = cartRepository.findElementsInCartByUserIdAndType(userId, "hotel");

		return hotelsInCartIds;
	}
	
	@Override
	@Transactional
	public void deleteElementFromCart(int reservationId, String elementType) throws ValidateException {
		checkIdInParam(reservationId);
		
		cartRepository.deleteByReservationIdAndType(reservationId, elementType);
	}
	
	
	private void checkAddElementToCart1Params(FlightReservationResource element, int userId) throws ValidateException {
		checkIdInParam(userId);
		if(element == null) {
			throw new ValidateException();
		}
	}
	
	private void checkAddElementToCart2Params(HotelReservationResource element, int userId) throws ValidateException {
		checkIdInParam(userId);
		if(element == null) {
			throw new ValidateException();
		}
	}
	
	private void checkIdInParam(int userId) throws ValidateException {
		if(userId < 1) {
			throw new ValidateException();
		}
	}


}
