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
import com.oreilly.cloud.object.BankDetails;
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.FlightTime;
import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.object.JourneyStage;
import com.oreilly.cloud.object.RoomResource;
import com.oreilly.cloud.repository.CartRepository;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {
	
	@InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private CartRepository cartRepository;
	
	
    /*
	 * TESTS on method addElementToCart(FlightReservationResource element, int userId) of CartService
	 */
    
    @Test
    public void flightAddToCartWithSuccess() {
        assertNotNull(cartService);
        FlightReservationResource element = createFlightReservationResource();
        int userId = 3;
        
        cartService.addElementToCart(element, userId);
    }
    
    @Test(expected = ValidateException.class)
    public void addFlightToCartValidateException() {
        assertNotNull(cartService);
        FlightReservationResource element = createFlightReservationResource();
        int userId = -2;
        
        cartService.addElementToCart(element, userId);
    }
	
    /*
	 * TESTS on method addElementToCart(HotelReservationResource element, int userId) of CartService
	 */
	
    @Test
    public void hotelAddToCartWithSuccess() {
        assertNotNull(cartService);
        HotelReservationResource element = createHotelReservationResource();
        int userId = 3;
        
        cartService.addElementToCart(element, userId);
    }
    
    @Test(expected = ValidateException.class)
    public void addHotelToCartValidateException() {
    	assertNotNull(cartService);
        HotelReservationResource element = createHotelReservationResource();
        int userId = -5;
        
        cartService.addElementToCart(element, userId);
    }
    
    /*
	 * TESTS on method checkIsInUserCart(int userId, int reservationId, String elementType) of CartService
	 */
    
    @Test
    public void foundReservationInCartWithUserIdReservationIdAndType() {
        assertNotNull(cartService);
        int userId = 2;
        int reservationId = 3;
        String elementType = "flight";
        
        when(cartRepository.findElementInCartByUserIdAndReservationIdAndType(userId, reservationId, elementType)).thenReturn(createReservationElement());
        
        cartService.checkIsInUserCart(userId, reservationId, elementType);
    }
    
    @Test(expected = ValidateException.class)
    public void checkIsInUserCartValidateException() {
        assertNotNull(cartService);
        int userId = 2;
        int reservationId = -3;
        String elementType = "flight";
        
        cartService.checkIsInUserCart(userId, reservationId, elementType);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void checkIsInUserCartResourceNotFoundException() {
        assertNotNull(cartService);
        int userId = 1;
        int reservationId = 3;
        String elementType = "flight";
        
        cartService.checkIsInUserCart(userId, reservationId, elementType);
    }
    
    /*
	 * TESTS on method List<Integer> getUserElementsInCart(int userId, String elementType) of CartService
	 */
   
    @Test
    public void foundElementIdsInCartWithUserIdAndType() {
        assertNotNull(cartService);
        int userId = 1;
        String elementType = "flight";
        
        when(cartRepository.findElementsInCartByUserIdAndType(userId, elementType)).thenReturn(createFlightReservationIdsList());
        
        List<Integer> userFlightInCartIds = cartService.getUserElementsInCart(userId, elementType);
        
        assertEquals(userFlightInCartIds.size(), 1);
        assertEquals(userFlightInCartIds.get(0), (Integer) 4);
    }
    
    @Test(expected = ValidateException.class)
    public void getUserElementsInCartValidateException() {
    	assertNotNull(cartService);
        int userId = -1;
        String elementType = "hotel";
        
        cartService.getUserElementsInCart(userId, elementType);
    }
    
    /*
	 * TESTS on method checkBankDetails(BankDetails bankDetails) of CartService
	 */
    
    @Test(expected = ValidateException.class)
    public void checkBankDetailsValidateException() {
        assertNotNull(cartService);
        BankDetails bankDetails = new BankDetails("visa", "", "Giovanni Neri", 5, 2021, 574);
        
        cartService.checkBankDetails(bankDetails);
    }
    
    /*
	 * TESTS on method confirmReservationInCart(int userId, int reservationId, String elementType) of CartService
	 */
    
    @Test(expected = ValidateException.class)
    public void confirmReservationInCartValidateException() {
        assertNotNull(cartService);
        int userId = -10;
        int reservationId = 3; 
        String elementType = "";
        
        cartService.confirmReservationInCart(userId, reservationId, elementType);
    }
    
	
	private FlightReservationResource createFlightReservationResource() {
		FlightReservationResource element = new FlightReservationResource();
		element.setReservationId(5);
		element.setUserEmail("giovannineri@yahoo.it");
		element.setFlight(createFlightResource());
		element.setPrice(58.36);
		element.setSeatsType("economy");
		element.setSeatsNumber(2);
		List<String> seats = new ArrayList<>();
		seats.add("3C");
		seats.add("6B");
		element.setSeats(seats);
		
		return element;
	}
	
	private FlightResource createFlightResource() {
    	FlightResource theFlight = new FlightResource();
    	theFlight.setFlightId(5);
    	theFlight.setCompany("easyJet");
    	JourneyStage source = new JourneyStage("Malpensa Airport", "MPX", "Milan", "Italy");
    	theFlight.setSource(source);
    	JourneyStage destination = new JourneyStage("London Gatwich Airport", "LGW", "London", "United Kingdom");
    	theFlight.setDestination(destination);
    	FlightTime departure = new FlightTime(10, 18, 13, 5, 2019);
    	theFlight.setDeparture(departure);
    	FlightTime arrival = new FlightTime(5, 19, 13, 5, 2019);
    	theFlight.setArrival(arrival);
    	theFlight.setAvailableEconomySeats(100);
    	theFlight.setAvailableBusinessSeats(56);
    	theFlight.setAvailableFirstSeats(0);
    	theFlight.setEconomySeatPrice(29.18);
    	theFlight.setBusinessSeatPrice(48.67);
    	theFlight.setFirstSeatPrice(0.0);
    	
	    return theFlight;
    }
	
	private HotelReservationResource createHotelReservationResource() {
		HotelReservationResource element = new HotelReservationResource();
		element.setReservationId(5);
		element.setUserEmail("giovannineri@yahoo.it");
		element.setRoom(createRoomResource());
		element.setPrice(352);
		element.setReservationType("with breakfast");
		element.setHostsNumber(2);
		CheckTime checkIn = new CheckTime(27, 3, 2019);
		element.setCheckIn(checkIn);
		CheckTime checkOut = new CheckTime(29, 3, 2019);
		element.setCheckOut(checkOut);
		
		return element;
	}
	
	private RoomResource createRoomResource() {
		RoomResource theRoom = new RoomResource();
		theRoom.setRoomId(5);
		theRoom.setHotel("Cheshire Hotel");
		theRoom.setHostsNumber(2);
		theRoom.setStandardDailyPrice(161.0);
		theRoom.setWithBreakfastDailyPrice(176.0);
		theRoom.setHalfBoardDailyPrice(0);
		theRoom.setFullBoardDailyPrice(0);
		theRoom.setSingleBeds(0);
		theRoom.setDoubleBeds(1);
		theRoom.setAirConditioner(true);
		theRoom.setHeat(true);
		theRoom.setTv(true);
		theRoom.setTelephone(true);
		theRoom.setBathtub(true);
		theRoom.setBathroom(true);;
		
		return theRoom;
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
	
	private ArrayList<Integer> createFlightReservationIdsList() {
		ArrayList<Integer> list = new ArrayList<>();
		list.add((Integer) 4);
		
		return list;
	}
	

}
