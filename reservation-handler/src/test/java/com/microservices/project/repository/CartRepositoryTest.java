package com.microservices.project.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.microservices.project.model.ReservationElement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartRepositoryTest {
	
	@Autowired
    private CartRepository cartRepository;
	
	
	/*
	 * TESTS on method save(ReservationElement newCartElement) of CartRepository
	 */
	
	@Test
	public void saveValidReservationElement() {
		assertNotNull(cartRepository);
		ReservationElement newCartElement = createReservationElement();
		
		ReservationElement savedCartElement = cartRepository.save(newCartElement);
		
		assertEquals(savedCartElement.getCartElementId(), newCartElement.getCartElementId());
		assertEquals(savedCartElement.getUserId(), newCartElement.getUserId());
	}
	
	/*
	 * TESTS on method findElementInCartByUserIdAndReservationIdAndType(int userId, int reservationId, String type) of CartRepository
	 */
	
	@Test
	public void findElementInCartWithValidUserIdReservationIdAndType() {
		assertNotNull(cartRepository);
		int userId = 2;
		int reservationId = 3;
		String type = "flight";
		
		ReservationElement cartElement = cartRepository.findElementInCartByUserIdAndReservationIdAndType(userId, reservationId, type);
		
		assertEquals(cartElement.getCartElementId(), 3);
		assertEquals(cartElement.getUserId(), userId);
		assertEquals(cartElement.getReservationId(), reservationId);
		assertEquals(cartElement.getType(), type);
	}
	
	@Test
	public void elementInCartNotFoundWithNotPresentReservationId() {
		assertNotNull(cartRepository);
		int userId = 2;
		int reservationId = 100000;
		String type = "flight";
		
		ReservationElement cartElement = cartRepository.findElementInCartByUserIdAndReservationIdAndType(userId, reservationId, type);
		
		assertNull(cartElement);
	}
	
	/*
	 * TESTS on method List<Integer> findElementsInCartByUserIdAndType(int userId, String type) of CartRepository
	 */
	
	@Test
	public void findElementIdsInCartWithValidUserIdAndType() {
		assertNotNull(cartRepository);
		int userId = 1;
		String type = "flight";
		
		List<Integer> cartElementIds = cartRepository.findElementsInCartByUserIdAndType(userId, type);
		
		assertEquals(cartElementIds.size(), 1);
		assertEquals(cartElementIds.get(0), (Integer) 4);
	}
	
	@Test
	public void elementIdsInCartNotFoundWithNotPresentUserId() {
		assertNotNull(cartRepository);
		int userId = 30000;
		String type = "flight";
		
		List<Integer> cartElementIds = cartRepository.findElementsInCartByUserIdAndType(userId, type);
		
		assertEquals(cartElementIds.size(), 0);
	}
	
	
	private ReservationElement createReservationElement() {
		ReservationElement newCartElement = new ReservationElement();
		newCartElement.setCartElementId(3);
		newCartElement.setUserId(2);
		newCartElement.setReservationId(3);
		newCartElement.setType("flight");
		newCartElement.setConfirmed(false);
		
		return newCartElement;
	}
		

}
