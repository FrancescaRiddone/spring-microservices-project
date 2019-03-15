package com.oreilly.cloud.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oreilly.cloud.model.ReservationElement;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistryRepositoryTest {
	
	@Autowired
    private RegistryRepository registryRepository;
	
	
	/*
	 * TESTS on method List<Integer> findElementsInRegistryByUserIdAndType(int userId, String type) of RegistryRepository
	 */
	
	@Test
	public void findElementIdsInRegistrytWithValidUserIdAndType() {
		assertNotNull(registryRepository);
		int userId = 1;
		String type = "hotel";
		
		List<Integer> registryElementIds = registryRepository.findElementsInRegistryByUserIdAndType(userId, type);
		
		assertEquals(registryElementIds.size(), 2);
		assertEquals(registryElementIds.get(0), (Integer) 1);
		assertEquals(registryElementIds.get(1), (Integer) 3);
	}
	
	@Test
	public void elementIdsInRegistryNotFoundWithNotPresentUserId() {
		assertNotNull(registryRepository);
		int userId = 30000;
		String type = "hotel";
		
		List<Integer> registryElementIds = registryRepository.findElementsInRegistryByUserIdAndType(userId, type);
		
		assertEquals(registryElementIds.size(), 0);
	}
	
	/*
	 * TESTS on method ReservationElement findElementInRegistryByUserIdAndReservationIdAndType(int userId, int reservationId, String type) of RegistryRepository
	 */
	
	@Test
	public void findElementInRegistryWithValidUserIdReservationIdAndType() {
		assertNotNull(registryRepository);
		int userId = 2;
		int reservationId = 2;
		String type = "flight";
		
		ReservationElement cartElement = registryRepository.findElementInRegistryByUserIdAndReservationIdAndType(userId, reservationId, type);
		
		assertEquals(cartElement.getCartElementId(), 2);
		assertEquals(cartElement.getUserId(), userId);
		assertEquals(cartElement.getReservationId(), reservationId);
		assertEquals(cartElement.getType(), type);
	}
	
	@Test
	public void elementInRegistryNotFoundWithNotPresentReservationId() {
		assertNotNull(registryRepository);
		int userId = 2;
		int reservationId = 100000;
		String type = "flight";
		
		ReservationElement cartElement = registryRepository.findElementInRegistryByUserIdAndReservationIdAndType(userId, reservationId, type);
		
		assertNull(cartElement);
	}
	
	
}
