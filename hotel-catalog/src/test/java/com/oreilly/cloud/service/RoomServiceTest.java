package com.oreilly.cloud.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.City;
import com.oreilly.cloud.model.Country;
import com.oreilly.cloud.model.Hotel;
import com.oreilly.cloud.model.QRoom;
import com.oreilly.cloud.model.Room;
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.HotelProperties;
import com.oreilly.cloud.object.RoomProperties;
import com.oreilly.cloud.object.RoomResource;
import com.oreilly.cloud.object.SearchHotelRequest;
import com.oreilly.cloud.repository.RoomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {
	
	@InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepository;
	
	
    /*
	 * TESTS on method List<Room> getRooms(SearchHotelRequest searchHotelRequest, List<Integer> hotelIds) of RoomService
	 */
    
    @Test
    public void foundRoomsWithSearchHotelRequestAndHotelIds() {
    	assertNotNull(roomService);
    	SearchHotelRequest searchHotelRequest = createSearchHotelRequest1(); 
    	List<Integer> hotelIds = new ArrayList<>();
    	hotelIds.add(1);
    	hotelIds.add(2);
    	hotelIds.add(5);
    	
    	when(roomRepository.findAll(createPredicate1())).thenReturn(createIterableRoom1());
    	List<Room> theRooms = roomService.getRooms(searchHotelRequest, hotelIds);
    	
    	assertNotNull(theRooms);
    	assertTrue(theRooms.size() == 1);
    	assertOnRoom(theRooms.get(0));
    }
    
    @Test(expected = ValidateException.class)
    public void getRoomsValidateException() {
    	assertNotNull(roomService);
    	SearchHotelRequest searchHotelRequest = createSearchHotelRequest1(); 
    	List<Integer> hotelIds = new ArrayList<>();
    	
    	roomService.getRooms(searchHotelRequest, hotelIds);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void getRoomsResourceNotFoundException() {
    	assertNotNull(roomService);
    	SearchHotelRequest searchHotelRequest = createSearchHotelRequest2(); 
    	List<Integer> hotelIds = new ArrayList<>();
    	for(int i = 1; i <= 5; i++) {
    		hotelIds.add(i);
    	}
    	
    	roomService.getRooms(searchHotelRequest, hotelIds);
    }
    
    /*
	 * TESTS on method List<Room> getRooms(int hotelId) of RoomService
	 */
    
    @Test
    public void foundRoomsWithHotelId() {
    	assertNotNull(roomService);
    	int hotelId = 4;
    	
    	when(roomRepository.findByHotelId(hotelId)).thenReturn(createRooms());
    	List<Room> theRooms = roomService.getRooms(hotelId);
    	
    	assertEquals(theRooms.get(0).getId(), 12);
    	assertEquals(theRooms.get(1).getId(), 13);
    	for(int i = 0; i < 2; i++) {
    		assertEquals(theRooms.get(i).getHotel().getId(), 4);
        	assertEquals(theRooms.get(i).getHotel().getCity().getName(), "London");
    	}
    }
    
    @Test
    public void roomsNotFoundWithInvalidHotelId() {
    	assertNotNull(roomService);
    	int hotelId = 0;
    	
    	List<Room> theRooms = roomService.getRooms(hotelId);
    	
    	assertTrue(theRooms == null);
    }
    
    /*
	 * TESTS on method Room getRoom(int roomId) of RoomService
	 */
    
    @Test
    public void foundRoomWithId() {
    	assertNotNull(roomService);
    	int roomId = 14;
    	
    	when(roomRepository.findById(roomId)).thenReturn(Optional.of(createRoom1()));
    	Room theRoom = roomService.getRoom(roomId);
    	
    	assertOnRoom(theRoom);
    }
    
    @Test(expected = ValidateException.class)
    public void getRoomValidateException() {
    	assertNotNull(roomService);
    	int roomId = 0;
    	
    	roomService.getRoom(roomId);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void getRoomResourceNotFoundException() {
    	assertNotNull(roomService);
    	int roomId = 10000;
    	
    	when(roomRepository.findById(roomId)).thenReturn(Optional.empty());
    	roomService.getRoom(roomId);
    }
    
    /*
	 * TESTS on method RoomResource getRoomResource(int roomId) of RoomService
	 */
    
    @Test
    public void foundRoomResourceWithId() {
    	assertNotNull(roomService);
    	int roomId = 14;
    	
    	when(roomRepository.findById(roomId)).thenReturn(Optional.of(createRoom1()));
    	RoomResource roomResource = roomService.getRoomResource(roomId);
    	
    	assertOnRoomResource(roomResource);
    }
    
    @Test(expected = ValidateException.class)
    public void getRoomResourceValidateException() {
    	assertNotNull(roomService);
    	int roomId = 0;
    	
    	roomService.getRoomResource(roomId);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void getRoomResourceWithResourceNotFoundException() {
    	assertNotNull(roomService);
    	int roomId = 10000;
    	
    	when(roomRepository.findById(roomId)).thenReturn(Optional.empty());
    	roomService.getRoomResource(roomId);
    }
    
    
	private Room createRoom1() {
		Room theRoom = new Room();
		theRoom.setId(14);
		theRoom.setHotel(createHotel1());
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
	
	private List<Room> createRooms(){
		List<Room> theRooms = new ArrayList<>();
		Hotel hotel = new Hotel();
		Country country = new Country(5, "United Kingdom");
		City city = new City(7, "London", country);
		hotel.setId(4);
		hotel.setName("Henry VIII");
		hotel.setCity(city);
		hotel.setCountry(country);
		
		Room room1 = new Room();
		room1.setId(12);
		room1.setHostsNumber(2);
		room1.setHotel(hotel);
		theRooms.add(room1);
		
		Room room2 = new Room();
		room2.setId(13);
		room2.setHostsNumber(2);
		room2.setHotel(hotel);
		theRooms.add(room2);
		
		return theRooms;
	}
	
    private SearchHotelRequest createSearchHotelRequest1() {
    	SearchHotelRequest request = new SearchHotelRequest();
    	request.setCity("London");
    	request.setCheckIn(new CheckTime(4, 10, 2019));
    	request.setCheckOut(new CheckTime(7, 10, 2019));
    	request.setHostsNumber(2);
    	HotelProperties hotelProperties = new HotelProperties();
    	hotelProperties.setReservationType("full board");
    	request.setHotelProperties(hotelProperties);
    	RoomProperties roomProperties = new RoomProperties();
    	roomProperties.setMinPrice(200.00);
    	request.setRoomProperties(roomProperties);
    	
    	return request;
    }
    
    private SearchHotelRequest createSearchHotelRequest2() {
    	SearchHotelRequest request = new SearchHotelRequest();
    	request.setCity("London");
    	request.setCheckIn(new CheckTime(4, 10, 2019));
    	request.setCheckOut(new CheckTime(7, 10, 2019));
    	request.setHostsNumber(2);
    	HotelProperties hotelProperties = new HotelProperties();
    	request.setHotelProperties(hotelProperties);
    	RoomProperties roomProperties = new RoomProperties();
    	roomProperties.setMinPrice(900000.00);
    	request.setRoomProperties(roomProperties);
    	
    	return request;
    }
    
    private Predicate createPredicate1() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QRoom room = QRoom.room;
		List<Integer> hotelIds = new ArrayList<>();
		hotelIds.add(1);
    	hotelIds.add(2);
    	hotelIds.add(5);
		
		predicate.and(room.hotel.id.in(hotelIds));
		predicate.and(room.hostsNumber.eq(2));
		predicate.and(room.fullBoardDailyPrice.goe(200.0));

    	return predicate;
    }
    
    private Iterable<Room> createIterableRoom1() {
    	List<Room> rooms = new ArrayList<>();
    	rooms.add(createRoom1());
    	
    	Iterable<Room> roomIterator = rooms;
    	
    	return roomIterator;
    }
    
    private void assertOnRoom(Room room) {
        assertNotNull(room);
        assertEquals(room.getId(), 14);
        assertEquals(room.getHotel().getId(), 5);
        assertEquals(room.getHotel().getCity().getName(), "London");
        assertEquals(room.getHotel().getName(), "Hilton London Bankside");
        assertEquals(room.getHostsNumber(), 2);
        assertTrue(room.getFullBoardDailyPrice() > 200.00);
    }
    
    private void assertOnRoomResource(RoomResource roomResource) {
        assertNotNull(roomResource);
        assertEquals(roomResource.getRoomId(), 14);
        assertEquals(roomResource.getHotel(), "Hilton London Bankside");
        assertEquals(roomResource.getHostsNumber(), 2);
        assertTrue(roomResource.getHalfBoardDailyPrice() > 200.00);
    }
    

}
