package com.oreilly.cloud.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oreilly.cloud.model.City;
import com.oreilly.cloud.model.Country;
import com.oreilly.cloud.model.Hotel;
import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.model.Room;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationRepositoryTest {
	
	@Autowired
    private ReservationRepository reservationRepository;
	
	
	/*
	 * TESTS on method List<Room> findReservedRooms(LocalDateTime startDate, LocalDateTime endDate, List<Integer> roomIds) of ReservationRepository
	 */
	
	@Test
	public void getReservedRoomsWithStartDateAndEndDateAndRoomIds() {
		assertNotNull(reservationRepository);
		LocalDateTime startDate = LocalDateTime.of(2019, 8, 10, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2019, 8, 15, 0, 0);
		List<Integer> roomIds = new ArrayList<>();
		roomIds.add(13);
		roomIds.add(14);
		
		List<Room> theRooms = reservationRepository.findReservedRooms(startDate, endDate, roomIds);
		
		assertNotNull(theRooms);
		assertTrue(theRooms.size() == 1);
		assertOnRoom(theRooms.get(0));
	}
	
	@Test
	public void getReservedRoomsNotFound() {
		assertNotNull(reservationRepository);
		LocalDateTime startDate = LocalDateTime.of(2019, 2, 10, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2019, 2, 12, 0, 0);
		List<Integer> roomIds = new ArrayList<>();
		roomIds.add(13);
		roomIds.add(14);
		
		List<Room> theRooms = reservationRepository.findReservedRooms(startDate, endDate, roomIds);
		
		assertNotNull(theRooms);
		assertTrue(theRooms.isEmpty());
	}
	
	/*
	 * TESTS on method Optional<Reservation> findById(int reservationId) of ReservationRepository
	 */
	
	@Test
	public void getReservationWithId() {
		assertNotNull(reservationRepository);
		int reservationId = 1;
		
		Optional<Reservation> theReservation = reservationRepository.findById(reservationId);
		
		assertTrue(theReservation.isPresent());
		assertOnReservation1(theReservation.get());
	}
	
	@Test
	public void getReservationWithInvalidId() {
		assertNotNull(reservationRepository);
		int reservationId = 0;
		
		Optional<Reservation> theReservation = reservationRepository.findById(reservationId);
		
		assertTrue(!theReservation.isPresent());
	}
	
	@Test
	public void getReservationWithNotFoundId() {
		assertNotNull(reservationRepository);
		int reservationId = 100000;
		
		Optional<Reservation> theReservation = reservationRepository.findById(reservationId);
		
		assertTrue(!theReservation.isPresent());
	}
	
	/*
	 * TESTS on method Reservation save(Reservation theReservation) of ReservationRepository
	 */
	
	@Test
	public void saveReservation() {
		assertNotNull(reservationRepository);
		Reservation theReservation = createReservation1();
		
		Reservation savedReservation = reservationRepository.save(theReservation);
		
		assertOnReservation2(savedReservation);
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
    	theReservation.setCheckIn(LocalDateTime.of(2019, 8, 2, 0, 0));
    	theReservation.setCheckOut(LocalDateTime.of(2019, 8, 10, 0, 0));
    	theReservation.setUserName("Mario");
    	theReservation.setUserSurname("Rossi");
    	theReservation.setPrice(3300.00);
    	theReservation.setHostsNumber(2);
    	theReservation.setReservationType("full board");
    	theReservation.setConfirmed(true);;
    	
    	return theReservation;
    }
	
	private void assertOnRoom(Room theRoom) {
		assertNotNull(theRoom);
		assertEquals(theRoom.getId(), 13);
		assertEquals(theRoom.getHostsNumber(), 2);
		assertEquals(theRoom.getHotel().getCity().getName(), "London");
		assertEquals(theRoom.getHotel().getId(), 4);
	}
	
	private void assertOnReservation1(Reservation theReservation) {
		assertNotNull(theReservation);
		assertEquals(theReservation.getId(), 1);
		assertEquals(theReservation.getRoom().getId(), 1);
		assertEquals(theReservation.getUserName(), "Mario");
		assertEquals(theReservation.getUserSurname(), "Rossi");
		assertEquals(theReservation.getHostsNumber(), 2);
	}
	
	private void assertOnReservation2(Reservation theReservation) {
		assertNotNull(theReservation);
		assertEquals(theReservation.getId(), 4);
		assertEquals(theReservation.getRoom().getId(), 14);
		assertEquals(theReservation.getUserName(), "Mario");
		assertEquals(theReservation.getUserSurname(), "Rossi");
		assertEquals(theReservation.getHostsNumber(), 2);
		assertEquals(theReservation.getReservationType(), "full board");
	}
	

}
