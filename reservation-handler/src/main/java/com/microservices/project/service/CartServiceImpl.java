package com.microservices.project.service;

import java.util.List;

import com.microservices.project.exception.ResourceNotFoundException;
import com.microservices.project.exception.ValidateException;
import com.microservices.project.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.project.model.ReservationElement;
import com.microservices.project.object.BankDetails;
import com.microservices.project.object.FlightReservationResource;
import com.microservices.project.object.HotelReservationResource;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepository;

	
	@Override
	@Transactional
	public void addElementToCart(FlightReservationResource element, int userId) throws ValidateException {
		checkAddElementToCart1Params(element, userId);
		ReservationElement newCartElement = new ReservationElement(userId, element.getReservationId(), "flight", false);
		
		cartRepository.save(newCartElement);
	}
	
	@Override
	@Transactional
	public void addElementToCart(HotelReservationResource element, int userId) throws ValidateException {
		checkAddElementToCart2Params(element, userId);
		ReservationElement newCartElement = new ReservationElement(userId, element.getReservationId(), "hotel", false);
		
		cartRepository.save(newCartElement);
	}
	
	@Override
	@Transactional
	public void checkIsInUserCart(int userId, int reservationId, String elementType) throws ValidateException, ResourceNotFoundException {
		checkIdsAndElementType(userId, reservationId, elementType);
		
		ReservationElement cartElement = cartRepository.findElementInCartByUserIdAndReservationIdAndType(userId, reservationId, elementType);
		
		if(cartElement == null) {
			throw new ResourceNotFoundException();
		}
	}
	
	@Override
	@Transactional
	public List<Integer> getUserElementsInCart(int userId, String elementType) throws ValidateException {
		checkIdInParam(userId);
		
		List<Integer> elementsInCartIds = cartRepository.findElementsInCartByUserIdAndType(userId, elementType);

		return elementsInCartIds;
	}
	
	@Override
	@Transactional
	public void deleteElementFromCart(int reservationId, String elementType) throws ValidateException {
		checkIdInParam(reservationId);
		
		cartRepository.deleteByReservationIdAndType(reservationId, elementType);
	}
	
	@Override
	public void checkBankDetails(BankDetails bankDetails) throws ValidateException {
		if(bankDetails == null) {
			throw new ValidateException();
		}
		if(bankDetails.getAccountNumber() == null || bankDetails.getCardOwner() == null ||
				bankDetails.getCardType() == null || bankDetails.getAccountNumber().equals("") ||
				bankDetails.getCardOwner().equals("") || bankDetails.getCardType().equals("") || 
				bankDetails.getExpiryYear() < 2019 ||
				bankDetails.getExpiryMonth() < 1 || bankDetails.getExpiryMonth() > 12) {
			throw new ValidateException();
		}
	}
	
	@Override
	@Transactional
	public void confirmReservationInCart(int userId, int reservationId, String elementType) throws ValidateException {
		checkIdsAndElementType(userId, reservationId, elementType);
		
		cartRepository.updateElementConfirmation(userId, reservationId, elementType);
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
	
	private void checkIdInParam(int id) throws ValidateException {
		if(id < 1) {
			throw new ValidateException();
		}
	}
	
	private void checkIdsAndElementType(int userId, int reservationId, String elementType) throws ValidateException {
		checkIdInParam(userId);
		checkIdInParam(reservationId);
		if(elementType == null) {
			throw new ValidateException();
		} else if(!elementType.equals("hotel") && !elementType.equals("flight")) {
			throw new ValidateException();
		}
	}


}
