package com.microservices.project.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.microservices.project.model.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.microservices.project.model.QRoom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomRepositoryTest {
	
	@Autowired
    private RoomRepository roomRepository;
	
	
	/*
	 * TESTS on method Iterable<Room> findAll(Predicate predicate) of RoomRepository
	 */

	@Test
	public void getRoomsWithHotelIdsAndHostsNumberAndBalcony() {
		assertNotNull(roomRepository);
		Predicate predicate = createPredicate1();
		
		Iterable<Room> roomsIterator = roomRepository.findAll(predicate);
		List<Room> foundRooms = Lists.newArrayList(roomsIterator);
		
		assertNotNull(foundRooms);
		assertEquals(foundRooms.size(), 1);
		assertOnRoom1(foundRooms.get(0));
	}
	
	@Test
	public void getRoomsNotFound() {
		assertNotNull(roomRepository);
		Predicate predicate = createPredicate2();
		
		Iterable<Room> roomsIterator = roomRepository.findAll(predicate);
		List<Room> foundRooms = Lists.newArrayList(roomsIterator);
		
		assertTrue(foundRooms.isEmpty());
	}
	
	/*
	 * TESTS on method List<Room> findByHotelId(int hotelId) of RoomRepository
	 */
	
	@Test
	public void getRoomWithHotelId() {
		assertNotNull(roomRepository);
		int hotelId = 5;
		
		List<Room> theRooms = roomRepository.findByHotelId(hotelId);
		
		assertNotNull(theRooms);
		assertTrue(theRooms.size() == 1);
		assertOnRoom1(theRooms.get(0));
	}
	
	@Test
	public void getRoomWithInvalidHotelId() {
		assertNotNull(roomRepository);
		int hotelId = 0;
		
		List<Room> theRooms = roomRepository.findByHotelId(hotelId);
		
		assertTrue(theRooms.isEmpty());
	}
	
	@Test
	public void getRoomWithNotFoundHotelId() {
		assertNotNull(roomRepository);
		int hotelId = 10000;
		
		List<Room> theRooms = roomRepository.findByHotelId(hotelId);
		
		assertTrue(theRooms.isEmpty());
	}
	
	/*
	 * TESTS on method Optional<Room> findById(int roomId) of RoomRepository
	 */
	
	@Test
	public void getRoomWithId() {
		assertNotNull(roomRepository);
		int roomId = 14;
		
		Optional<Room> theRoom = roomRepository.findById(roomId);
		
		assertTrue(theRoom.isPresent());
		assertOnRoom1(theRoom.get());
	}
	
	@Test
	public void getRoomWithInvalidId() {
		assertNotNull(roomRepository);
		int roomId = 0;
		
		Optional<Room> theRoom = roomRepository.findById(roomId);
		
		assertTrue(!theRoom.isPresent());
	}
	
	@Test
	public void getRoomWithNotFoundId() {
		assertNotNull(roomRepository);
		int roomId = 100000;
		
		Optional<Room> theRoom = roomRepository.findById(roomId);
		
		assertTrue(!theRoom.isPresent());
	}
	
	
	private Predicate createPredicate1() {
		BooleanBuilder predicate = new BooleanBuilder();
		QRoom room = QRoom.room;
		
		List<Integer> hotelIds = new ArrayList<>();
		hotelIds.add(3);
		hotelIds.add(5);
		predicate.and(room.hotel.id.in(hotelIds));
		predicate.and(room.hostsNumber.eq(2));
		predicate.and(room.balcony.eq(true));
		
		return predicate;
	}
	
	private Predicate createPredicate2() {
		BooleanBuilder predicate = new BooleanBuilder();
		QRoom room = QRoom.room;
		
		List<Integer> hotelIds = new ArrayList<>();
		hotelIds.add(1);
		hotelIds.add(2);
		predicate.and(room.hotel.id.in(hotelIds));
		predicate.and(room.hostsNumber.eq(4));
		predicate.and(room.balcony.eq(true));
		
		return predicate;
	}
	
	private void assertOnRoom1(Room theRoom) {
		assertNotNull(theRoom);
		assertEquals(theRoom.getId(), 14);
		assertTrue(theRoom.getHotel().getId() == 3 || theRoom.getHotel().getId() == 5);
		assertEquals(theRoom.getHostsNumber(), 2);
		assertTrue(theRoom.isBalcony());
	}
	

}
