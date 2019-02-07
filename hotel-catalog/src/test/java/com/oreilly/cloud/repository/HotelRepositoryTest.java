package com.oreilly.cloud.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.oreilly.cloud.model.Hotel;
import com.oreilly.cloud.model.QHotel;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelRepositoryTest {
	
	@Autowired
    private HotelRepository hotelRepository;
	
	
	/*
	 * TESTS on method Optional<Hotel> findById(int id) of HotelRepository
	 */
	
	@Test
	public void getHotelByValidId() {
		assertNotNull(hotelRepository);
		int hotelId = 1;
		
		Optional<Hotel> theHotel = hotelRepository.findById(hotelId);
		
		assertTrue(theHotel.isPresent());
		assertOnHotel1(theHotel.get());
	}
	
	@Test
	public void getHotelByInvalidId() {
		assertNotNull(hotelRepository);
		int hotelId = -1;
		
		Optional<Hotel> theHotel = hotelRepository.findById(hotelId);
		
		assertTrue(!theHotel.isPresent());
	}
	
	@Test
	public void getHotelByIdNotFound() {
		assertNotNull(hotelRepository);
		int hotelId = 40000;
		
		Optional<Hotel> theHotel = hotelRepository.findById(hotelId);
		
		assertTrue(!theHotel.isPresent());
	}
	
	/*
	 * TESTS on method Iterable<Hotel> findAll(Predicate predicate) of HotelRepository
	 */
	
	@Test
	public void getHotelsWithCityAndStars() {
		assertNotNull(hotelRepository);
		Predicate predicate = createPredicate1();
		
		Iterable<Hotel> hotelsIterator = hotelRepository.findAll(predicate);
		List<Hotel> foundHotels = Lists.newArrayList(hotelsIterator);
		
		assertNotNull(foundHotels);
		assertEquals(foundHotels.size(), 1);
		assertOnHotel2(foundHotels.get(0));
	}
	
	@Test
	public void getHotelsNotFound() {
		assertNotNull(hotelRepository);
		Predicate predicate = createPredicate2();
		
		Iterable<Hotel> hotelsIterator = hotelRepository.findAll(predicate);
		List<Hotel> foundHotels = Lists.newArrayList(hotelsIterator);
		
		assertTrue(foundHotels.isEmpty());
	}
	
	
	private Predicate createPredicate1() {
		BooleanBuilder predicate = new BooleanBuilder();
		QHotel hotel = QHotel.hotel;
		
		predicate.and(hotel.city.name.equalsIgnoreCase("London"));
		predicate.and(hotel.stars.eq(5));
		
		return predicate;
	}
	
	private Predicate createPredicate2() {
		BooleanBuilder predicate = new BooleanBuilder();
		QHotel hotel = QHotel.hotel;
		
		predicate.and(hotel.city.name.equalsIgnoreCase("Carmagnola"));
		predicate.and(hotel.stars.eq(5));
		
		return predicate;
	}
	
	private void assertOnHotel1(Hotel theHotel) {
		assertNotNull(theHotel);
		assertEquals(theHotel.getId(), 1);
		assertEquals(theHotel.getName(), "St Giles London, A St Giles Hotel");
		assertEquals(theHotel.getStars(), 3);
		assertEquals(theHotel.getCity().getName(), "London");
		assertEquals(theHotel.getCountry().getName(), "United Kingdom");
	}
	
	private void assertOnHotel2(Hotel theHotel) {
		assertNotNull(theHotel);
		assertEquals(theHotel.getId(), 5);
		assertEquals(theHotel.getName(), "Hilton London Bankside");
		assertEquals(theHotel.getStars(), 5);
		assertEquals(theHotel.getCity().getName(), "London");
		assertEquals(theHotel.getCountry().getName(), "United Kingdom");
	}

	
}
