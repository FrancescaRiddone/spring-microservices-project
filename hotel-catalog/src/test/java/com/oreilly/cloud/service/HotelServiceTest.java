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
import com.oreilly.cloud.model.QHotel;
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.HotelProperties;
import com.oreilly.cloud.object.HotelResource;
import com.oreilly.cloud.object.RoomProperties;
import com.oreilly.cloud.object.SearchHotelRequest;
import com.oreilly.cloud.repository.HotelRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@RunWith(MockitoJUnitRunner.class)
public class HotelServiceTest {
	
	@InjectMocks
    private HotelServiceImpl hotelService;

    @Mock
    private HotelRepository hotelRepository;

    
    /*
	 * TESTS on method HotelResource getHotel(int hotelId) of HotelService
	 */
    
    @Test
    public void hotelFoundWithId() {
    	assertNotNull(hotelService);
    	int hotelId = 1;
    	
    	when(hotelRepository.findById(hotelId)).thenReturn(createOptionalHotel());
    	HotelResource hotel = hotelService.getHotel(hotelId);
    	
    	assertOnHotel(hotel, hotelId);
    }
    
    @Test(expected = ValidateException.class)
    public void getHotelValidateException() {
    	assertNotNull(hotelService);
    	int hotelId = 0;
    	
        hotelService.getHotel(hotelId);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void flightNotFound() {
    	assertNotNull(hotelService);
    	int hotelId = 100000;
        
    	when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());
    	hotelService.getHotel(hotelId);
    }
    
    /*
	 * TESTS on method List<HotelResource> getHotels(SearchHotelRequest searchHotelRequest) of HotelService
	 */

    @Test
    public void hotelsFoundWithCityAndReservationType() {
    	assertNotNull(hotelService);
        SearchHotelRequest searchHotelRequest = createSearchHotelRequest1(); 
        
        when(hotelRepository.findAll(createPredicate1())).thenReturn(createIterableHotel1());
        List<HotelResource> hotels = hotelService.getHotels(searchHotelRequest);
        
        assertNotNull(hotels);
        assertTrue(hotels.size() == 1);
        assertEquals(hotels.get(0).getHotelId(), 5);
        assertEquals(hotels.get(0).getCity(), "London");
        assertTrue(hotels.get(0).isFullBoardAvailable());
    }
    
    @Test(expected = ValidateException.class)
    public void getHotelsValidateException() {
    	assertNotNull(hotelService);
    	
    	SearchHotelRequest searchHotelRequest = new SearchHotelRequest(); 
    	
    	hotelService.getHotels(searchHotelRequest);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void getHotelsResourceNotFoundException() {
    	assertNotNull(hotelService);
    	SearchHotelRequest searchHotelRequest = createSearchHotelRequest2();
        
        hotelService.getHotels(searchHotelRequest);
    }
    
    
    private Optional<Hotel> createOptionalHotel() {
    	Optional<Hotel> optHotel = Optional.of(createHotel1());
    	
    	return optHotel;
    }
    
    private Hotel createHotel1() {
    	Hotel theHotel = new Hotel();
    	Country theCountry = new Country(5, "United Kingdom");
    	City theCity = new City(7, "London", theCountry);
    	
    	theHotel.setId(1);
    	theHotel.setName("St Giles London, A St Giles Hotel");
    	theHotel.setCity(theCity);
    	theHotel.setCountry(theCountry);
    	theHotel.setAddress("Bedford Avenue, Camden, Londra, WC1B 3GH, Regno Unito");
    	theHotel.setStars(3);
    	theHotel.setWifi(true);
    	theHotel.setParking(false);
    	theHotel.setRestaurant(false);
    	theHotel.setForDisabledPeople(true);
    	theHotel.setGym(true);
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
    
    private SearchHotelRequest createSearchHotelRequest1() {
    	SearchHotelRequest request = new SearchHotelRequest();
    	request.setCity("London");
    	request.setCheckIn(new CheckTime(4, 10, 2019));
    	request.setCheckOut(new CheckTime(7, 10, 2019));
    	request.setHostsNumber(3);
    	HotelProperties hotelProperties = new HotelProperties();
    	hotelProperties.setReservationType("full board");
    	request.setHotelProperties(hotelProperties);
    	RoomProperties roomProperties = new RoomProperties();
    	roomProperties.setMaxPrice(200.00);
    	request.setRoomProperties(roomProperties);
    	
    	return request;
    }
    
    private SearchHotelRequest createSearchHotelRequest2() {
    	SearchHotelRequest request = new SearchHotelRequest();
    	request.setCity("Carmagnola");
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
    
    private Predicate createPredicate1() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QHotel hotel = QHotel.hotel;
		
		predicate.and(hotel.city.name.equalsIgnoreCase("London"));
		predicate.and(hotel.fullBoardAvailability.eq(true));

    	return predicate;
    }
    
    private Iterable<Hotel> createIterableHotel1() {
    	List<Hotel> hotels = new ArrayList<>();
    	hotels.add(createHotel2());
    	
    	Iterable<Hotel> hotelIterator = hotels;
    	
    	return hotelIterator;
    }
    
    private void assertOnHotel(HotelResource hotel, int hotelId) {
        assertNotNull(hotel);
        assertEquals(hotel.getHotelId(), hotelId);
        assertEquals(hotel.getCity(), "London");
        assertEquals(hotel.getHotelName(), "St Giles London, A St Giles Hotel");
        assertEquals(hotel.getAddress(), "Bedford Avenue, Camden, Londra, WC1B 3GH, Regno Unito");
        assertEquals(hotel.getStars(), 3);
    }
    
    
}
