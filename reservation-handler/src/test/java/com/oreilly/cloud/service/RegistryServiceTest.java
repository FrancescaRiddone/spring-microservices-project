package com.oreilly.cloud.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.ReservationElement;
import com.oreilly.cloud.repository.RegistryRepository;

@RunWith(MockitoJUnitRunner.class)
public class RegistryServiceTest {
	
	@InjectMocks
    private RegistryServiceImpl registryService;

    @Mock
    private RegistryRepository registryRepository;
	
	
    /*
	 * TESTS on method List<Integer> getUserElementsInRegistry(int userId, String elementType) of RegistryService
	 */
	
    @Test
    public void foundElementIdsInRegistryWithUserIdAndType() {
        assertNotNull(registryService);
        int userId = 1;
        String elementType = "hotel";
        
        when(registryRepository.findElementsInRegistryByUserIdAndType(userId, elementType)).thenReturn(createFlightReservationIdsList());
        
        List<Integer> userHotelInRegistryIds = registryService.getUserElementsInRegistry(userId, elementType);
        
        assertEquals(userHotelInRegistryIds.size(), 2);
        assertEquals(userHotelInRegistryIds.get(0), (Integer) 1);
        assertEquals(userHotelInRegistryIds.get(1), (Integer) 3);
    }
    
    @Test(expected = ValidateException.class)
    public void getUserElementsInRegistryValidateException() {
    	assertNotNull(registryService);
        int userId = -1;
        String elementType = "flight";
        
        registryService.getUserElementsInRegistry(userId, elementType);
    }
	
    /*
	 * TESTS on method checkIsInUserRegistry(int userId, int reservationId, String elementType) of RegistryService
	 */
	
    @Test
    public void foundReservationInRegistryWithUserIdReservationIdAndType() {
        assertNotNull(registryService);
        int userId = 1;
        int reservationId = 1;
        String elementType = "hotel";
        
        when(registryRepository.findElementInRegistryByUserIdAndReservationIdAndType(userId, reservationId, elementType)).thenReturn(createReservationElement());
        
        registryService.checkIsInUserRegistry(userId, reservationId, elementType);
    }
    
    @Test(expected = ValidateException.class)
    public void checkIsInUserCartValidateException() {
        assertNotNull(registryService);
        int userId = 2;
        int reservationId = -3;
        String elementType = "flight";
        
        registryService.checkIsInUserRegistry(userId, reservationId, elementType);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void checkIsInUserCartResourceNotFoundException() {
        assertNotNull(registryService);
        int userId = 2;
        int reservationId = 1;
        String elementType = "flight";
        
        registryService.checkIsInUserRegistry(userId, reservationId, elementType);
    }
	
	
    private ArrayList<Integer> createFlightReservationIdsList() {
		ArrayList<Integer> list = new ArrayList<>();
		list.add((Integer) 1);
		list.add((Integer) 3);
		
		return list;
	}
    
    private ReservationElement createReservationElement() {
		ReservationElement newCartElement = new ReservationElement();
		newCartElement.setCartElementId(5);
		newCartElement.setUserId(1);
		newCartElement.setReservationId(1);
		newCartElement.setType("hotel");
		newCartElement.setConfirmed(true);
		
		return newCartElement;
	}
	

}
