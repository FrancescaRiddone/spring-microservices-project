package com.oreilly.cloud.service;

import java.util.List;

public interface RegistryService {
	
	public List<Integer> getUserElementsInRegistry(int userId, String elementType);
	
	public void checkIsInUserRegistry(int userId, int reservationId, String elementType);

}
