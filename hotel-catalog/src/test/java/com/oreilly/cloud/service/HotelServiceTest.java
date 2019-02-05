package com.oreilly.cloud.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

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
    /*
    @Test
    public void hotelsFoundWithCityAndHostNumberAndDatesAndMaxPrice() {
    	assertNotNull(hotelService);
        SearchHotelRequest searchHotelRequest = createSearchHotelRequest(); 
        
        when(hotelRepository.findAll(createPredicate1())).thenReturn(createFlights(2));
        List<HotelResource> flights = hotelService.getHotels(searchHotelRequest);
        
        assertNotNull(flights);
        assertThat(flights.size(), is(1));
        assertOnFlight(flights.get(0), 2);
        assertTrue(flights.get(0).getDeparture().getHour() >= 9);
    }
    */
    
    
    
    
    
    
    
    private Optional<Hotel> createOptionalHotel() {
    	Optional<Hotel> optHotel = Optional.of(createHotel());
    	
    	return optHotel;
    }
    
    private Hotel createHotel() {
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
    
    private SearchHotelRequest createSearchHotelRequest() {
    	SearchHotelRequest request = new SearchHotelRequest();
    	request.setCity("London");
    	request.setCheckIn(new CheckTime(4, 10, 2019));
    	request.setCheckIn(new CheckTime(7, 10, 2019));
    	request.setHostsNumber(3);
    	HotelProperties hotelProperties = new HotelProperties();
    	hotelProperties.setReservationType("with breakfast");
    	request.setHotelProperties(hotelProperties);
    	RoomProperties roomProperties = new RoomProperties();
    	roomProperties.setMaxPrice(200.00);
    	request.setRoomProperties(roomProperties);
    	
    	return request;
    }
    
    private Predicate createPredicate1() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QHotel hotel = QHotel.hotel;
		
		predicate.and(hotel.city.name.equalsIgnoreCase("London"));
		predicate.and(hotel.breakfastAvailability.eq(true));

    	return predicate;
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
