package com.oreilly.cloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.ReservationElement;
import com.oreilly.cloud.repository.RegistryRepository;

@Service
public class RegistryServiceImpl implements RegistryService {
	
	@Autowired
	private RegistryRepository registryRepository;
	
	
	@Override
	@Transactional
	public List<Integer> getUserElementsInRegistry(int userId, String elementType) throws ValidateException {
		checkGetUserElementsInRegistryParam(userId, elementType);
		
		List<Integer> elementsInRegistryIds = registryRepository.findElementsInRegistryByUserIdAndType(userId, elementType);

		return elementsInRegistryIds;
	}
	
	@Override
	@Transactional
	public void checkIsInUserRegistry(int userId, int reservationId, String elementType) throws ValidateException, ResourceNotFoundException {
		checkIdsAndElementType(userId, reservationId, elementType);
		
		ReservationElement registryElement = registryRepository.findElementInRegistryByUserIdAndReservationIdAndType(userId, reservationId, elementType);
		
		if(registryElement == null) {
			throw new ResourceNotFoundException();
		}
	}
	

	private void checkIdInParam(int id) throws ValidateException {
		if(id < 1) {
			throw new ValidateException();
		}
	}
	
	private void checkGetUserElementsInRegistryParam(int userId, String elementType) {
		checkIdInParam(userId);
		if(elementType == null || elementType.equals("")) {
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
