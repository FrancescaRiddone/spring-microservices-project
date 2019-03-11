package com.oreilly.cloud.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ResourceUnavailableException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.City;
import com.oreilly.cloud.model.Country;
import com.oreilly.cloud.model.Hotel;
import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.model.Room;
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.repository.ReservationRepository;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {
	
	@InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;
	
	
    /*
	 * TESTS on method List<Room> getReservedRooms(CheckTime checkIn, CheckTime checkOut, List<Integer> roomIds) of ReservationService
	 */
	
    @Test
    public void foundReservedRoomsWithIdsAndCheckTimes() {
    	assertNotNull(reservationService);
    	CheckTime checkIn = new CheckTime(10, 8, 2019);
    	CheckTime checkOut = new CheckTime(16, 8, 2019);
		LocalDateTime startDate = LocalDateTime.of(2019, 8, 10, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2019, 8, 15, 0, 0);
		List<Integer> roomIds = new ArrayList<>();
		roomIds.add(13);
		roomIds.add(14);
    	
    	when(reservationRepository.findReservedRooms(startDate, endDate, roomIds)).thenReturn(createRooms1());
    	List<Room> theReservedRooms = reservationService.getReservedRooms(checkIn, checkOut, roomIds);
    	
    	assertTrue(theReservedRooms.size() == 1);
    	assertEquals(theReservedRooms.get(0).getId(), 13);
    }
    
    @Test(expected = ValidateException.class)
    public void getReservedRoomsValidateException() {
    	assertNotNull(reservationService);
    	CheckTime checkIn = new CheckTime(12, 8, 2019);
    	CheckTime checkOut = new CheckTime(2, 8, 2019);
    	List<Integer> roomIds = new ArrayList<>();
    	roomIds.add(13);
    	roomIds.add(14);
    	
    	reservationService.getReservedRooms(checkIn, checkOut, roomIds);
    }
    
    @Test
    public void reservedRoomsNotFound() {
    	assertNotNull(reservationService);
    	CheckTime checkIn = new CheckTime(7, 3, 2019);
    	CheckTime checkOut = new CheckTime(12, 3, 2019);
    	List<Integer> roomIds = new ArrayList<>();
    	roomIds.add(13);
    	roomIds.add(14);
    	
    	List<Room> theReservedRooms = reservationService.getReservedRooms(checkIn, checkOut, roomIds);
    	
    	assertTrue(theReservedRooms.isEmpty());
    }
    
    /*
	 * TESTS on method Reservation getReservation(int reservationId) of ReservationService
	 */
    
    @Test
    public void foundReservationWithId() {
    	assertNotNull(reservationService);
    	int reservationId = 4;
    	
    	when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(createReservation1()));
    	Reservation theReservation = reservationService.getReservation(reservationId);
    	
    	assertOnReservation(theReservation);
    }
    
    @Test(expected = ValidateException.class)
    public void getReservationValidateException() {
    	assertNotNull(reservationService);
    	int reservationId = 0;
    	
    	reservationService.getReservation(reservationId);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void getReservationResourceNotFoundException() {
    	assertNotNull(reservationService);
    	int reservationId = 500000;
    	
    	when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());
    	reservationService.getReservation(reservationId);
    }
    
    /*
	 * TESTS on method HotelReservationResource getReservationResource(int reservationId) of ReservationService
	 */
    
    @Test
    public void foundHotelReservationResourceWithId() {
    	assertNotNull(reservationService);
    	int reservationId = 4;
    	
    	when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(createReservation1()));
    	HotelReservationResource reservationResource = reservationService.getReservationResource(reservationId);
    	
    	assertOnReservationResource1(reservationResource);
    }
    
    @Test(expected = ValidateException.class)
    public void getReservationResourceValidateException() {
    	assertNotNull(reservationService);
    	int reservationId = 0;
    	
    	reservationService.getReservationResource(reservationId);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void getReservationResourceResourceNotFoundException() {
    	assertNotNull(reservationService);
    	int reservationId = 500000;
    	
    	when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());
    	reservationService.getReservationResource(reservationId);
    }
    
    /*
	 * TESTS on method void checkRoomAvailability(int roomId, int hostsNumber, CheckTime checkIn, CheckTime checkOut) of ReservationService
	 */
    
    @Test
    public void foundRoomAvailable() {
    	assertNotNull(reservationService);
    	int roomId = 1;
    	int hostsNumber = 2;
    	CheckTime checkIn = new CheckTime(20, 10, 2019);
    	CheckTime checkOut = new CheckTime(22, 10, 2019);
		List<Integer> roomIds = new ArrayList<>();
    	roomIds.add(roomId);
    	
    	reservationService.checkRoomAvailability(roomId, hostsNumber, checkIn, checkOut);
    }
    
    @Test(expected = ValidateException.class)
    public void checkRoomAvailabilityValidateException() {
    	assertNotNull(reservationService);
    	int roomId = 0;
		int hostsNumber = 0;
		CheckTime checkIn = new CheckTime(7, 8, 2019);
    	CheckTime checkOut = new CheckTime(12, 8, 2019);
    	
    	reservationService.checkRoomAvailability(roomId, hostsNumber, checkIn, checkOut);
    }

    @Test(expected = ResourceUnavailableException.class)
    public void roomUnavailableWithResourceUnavailableException() {
    	assertNotNull(reservationService);
    	CheckTime checkIn = new CheckTime(7, 8, 2019);
    	CheckTime checkOut = new CheckTime(12, 8, 2019);
    	List<Integer> roomIds = new ArrayList<>();
    	roomIds.add(13);
    	LocalDateTime startDate = LocalDateTime.of(2019, 8, 7, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2019, 8, 11, 0, 0);
		int roomId = 13;
    	int hostsNumber = 2;
		
		when(reservationRepository.findReservedRooms(startDate, endDate, roomIds)).thenReturn(createRooms1());
    	reservationService.checkRoomAvailability(roomId, hostsNumber, checkIn, checkOut);    	
    }
    
    /*
	 * TESTS on method HotelReservationResource saveReservation(Reservation theReservation) of ReservationService
	 */
    
    @Test
    public void reservationSaved() {
    	assertNotNull(reservationService);
    	Reservation theReservation = createReservation2();
    	
    	when(reservationRepository.save(theReservation)).thenReturn(createReservation2());
    	HotelReservationResource savedReservation = reservationService.saveReservation(theReservation);
    	
    	assertOnReservationResource2(savedReservation);
    }
    
    @Test(expected = ValidateException.class)
    public void saveReservationValidateException() {
    	assertNotNull(reservationService);
    	Reservation theReservation = new Reservation();
    	
    	reservationService.saveReservation(theReservation);
    }
    
    
    private List<Room> createRooms1(){
    	List<Room> theRooms = new ArrayList<>();
    	theRooms.add(createRoom1());
    	
    	return theRooms;
    }
    
    private Room createRoom1() {
		Room theRoom = new Room();
		theRoom.setId(13);
		theRoom.setHotel(createHotel1());
		theRoom.setHostsNumber(2);
		theRoom.setStandardDailyPrice(280.00);
		theRoom.setWithBreakfastDailyPrice(350.00);
		theRoom.setSingleBeds(2);
		theRoom.setDoubleBeds(0);
		theRoom.setAirConditioner(true);
		theRoom.setHeat(true);
		theRoom.setTv(true);
		theRoom.setTelephone(true);
		theRoom.setVault(true);
		theRoom.setBathtub(true);
		theRoom.setSwimmingPool(false);
		theRoom.setSoundproofing(true);
		theRoom.setWithView(true);
		theRoom.setBathroom(true);
		theRoom.setBalcony(true);
		
		return theRoom;
	}
	
    private Room createRoom2() {
		Room theRoom = new Room();
		theRoom.setId(14);
		theRoom.setHotel(createHotel2());
		theRoom.setHostsNumber(2);
		theRoom.setStandardDailyPrice(400.00);
		theRoom.setWithBreakfastDailyPrice(450.00);
		theRoom.setHalfBoardDailyPrice(490.00);
		theRoom.setFullBoardDailyPrice(550.0);
		theRoom.setSingleBeds(2);
		theRoom.setDoubleBeds(0);
		theRoom.setAirConditioner(true);
		theRoom.setHeat(true);
		theRoom.setTv(true);
		theRoom.setTelephone(true);
		theRoom.setVault(true);
		theRoom.setBathtub(true);
		theRoom.setSwimmingPool(true);
		theRoom.setSoundproofing(true);
		theRoom.setWithView(true);
		theRoom.setBathroom(true);
		theRoom.setBalcony(true);
		
		return theRoom;
	}
    
    private Hotel createHotel1() {
    	Hotel theHotel = new Hotel();
    	Country theCountry = new Country(5, "United Kingdom");
    	City theCity = new City(7, "London", theCountry);
    	
    	theHotel.setId(4);
    	theHotel.setName("Henry VIII");
    	theHotel.setCity(theCity);
    	theHotel.setCountry(theCountry);
    	theHotel.setAddress("23 Leinster Gardens, Quartiere di Westminster, Londra, W2 3AN, Regno Unito");
    	theHotel.setStars(4);
    	theHotel.setWifi(true);
    	theHotel.setParking(false);
    	theHotel.setRestaurant(true);
    	theHotel.setForDisabledPeople(true);
    	theHotel.setGym(false);
    	theHotel.setSpa(false);
    	theHotel.setSwimmingPool(true);
    	theHotel.setBreakfastAvailability(true);
    	theHotel.setHalfBoardAvailability(false);
    	theHotel.setFullBoardAvailability(false);
    	
    	return theHotel;
    }
    
    private Hotel createHotel2() {
    	Hotel theHotel = new Hotel();
    	Country theCountry = new Country(5, "United Kingdom");
    	City theCity = new City(7, "London", theCountry);
    	
    	theHotel.setId(5);
    	theHotel.setName("Hilton London Bankside");
    	theHotel.setCity(theCity);
    	theHotel.setCountry(theCountry);
    	theHotel.setAddress("2-8 Great Suffolk Street, Southwark, Londra, SE1 0UG, Regno Unito");
    	theHotel.setStars(5);
    	theHotel.setWifi(true);
    	theHotel.setParking(true);
    	theHotel.setRestaurant(true);
    	theHotel.setForDisabledPeople(true);
    	theHotel.setGym(true);
    	theHotel.setSpa(true);
    	theHotel.setSwimmingPool(true);
    	theHotel.setBreakfastAvailability(true);
    	theHotel.setHalfBoardAvailability(true);
    	theHotel.setFullBoardAvailability(true);
    	
    	return theHotel;
    }
	
    private Reservation createReservation1() {
    	Reservation theReservation = new Reservation();
    	theReservation.setId(4);
    	theReservation.setRoom(createRoom2());
    	theReservation.setCheckIn(LocalDateTime.of(2019, 5, 13, 0, 0));
    	theReservation.setCheckOut(LocalDateTime.of(2019, 5, 15, 0, 0));
    	theReservation.setUserEmail("mariorossi@yahoo.it");
    	theReservation.setPrice(320.00);
    	theReservation.setHostsNumber(2);
    	theReservation.setReservationType("with breakfast");
    	theReservation.setConfirmed(true);;
    	
    	return theReservation;
    }
    
    private Reservation createReservation2() {
    	Reservation theReservation = new Reservation();
    	theReservation.setId(10000);
    	theReservation.setRoom(createRoom2());
    	theReservation.setCheckIn(LocalDateTime.of(2019, 2, 13, 0, 0));
    	theReservation.setCheckOut(LocalDateTime.of(2019, 2, 15, 0, 0));
    	theReservation.setUserEmail("marcoblu@yahoo.it");
    	theReservation.setPrice(320.00);
    	theReservation.setHostsNumber(2);
    	theReservation.setReservationType("with breakfast");
    	theReservation.setConfirmed(true);;
    	
    	return theReservation;
    }
    
    private void assertOnReservation(Reservation theReservation) {
    	assertNotNull(theReservation);
    	assertEquals(theReservation.getId(), 4);
    	assertEquals(theReservation.getCheckIn(), LocalDateTime.of(2019, 5, 13, 0, 0));
    	assertEquals(theReservation.getCheckOut(), LocalDateTime.of(2019, 5, 15, 0, 0));
    	theReservation.setUserEmail("mariorossi@yahoo.it");
    }
    
    private void assertOnReservationResource1(HotelReservationResource reservationResource) {
    	assertNotNull(reservationResource);
    	assertEquals(reservationResource.getReservationId(), 4);
    	assertTrue(reservationResource.getPrice() == 320.00);
    	assertEquals(reservationResource.getCheckIn().getDay(), 13);
    	assertEquals(reservationResource.getCheckIn().getMonth(), 5);
    	assertEquals(reservationResource.getCheckIn().getYear(), 2019);
    	assertEquals(reservationResource.getCheckOut().getDay(), 15);
    	assertEquals(reservationResource.getCheckOut().getMonth(), 5);
    	assertEquals(reservationResource.getCheckOut().getYear(), 2019);
    }
    
    private void assertOnReservationResource2(HotelReservationResource reservationResource) {
    	assertNotNull(reservationResource);
    	assertEquals(reservationResource.getReservationId(), 10000);
    	assertTrue(reservationResource.getPrice() == 320.00);
    	assertEquals(reservationResource.getCheckIn().getDay(), 13);
    	assertEquals(reservationResource.getCheckIn().getMonth(), 2);
    	assertEquals(reservationResource.getCheckIn().getYear(), 2019);
    	assertEquals(reservationResource.getCheckOut().getDay(), 15);
    	assertEquals(reservationResource.getCheckOut().getMonth(), 2);
    	assertEquals(reservationResource.getCheckOut().getYear(), 2019);
    }
    

}
