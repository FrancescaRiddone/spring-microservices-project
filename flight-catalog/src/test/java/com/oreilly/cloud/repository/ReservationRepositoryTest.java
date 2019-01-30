package com.oreilly.cloud.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oreilly.cloud.model.Airport;
import com.oreilly.cloud.model.City;
import com.oreilly.cloud.model.Company;
import com.oreilly.cloud.model.Country;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.model.Reservation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationRepositoryTest {
	
	@Autowired
    private ReservationRepository reservationRepository;
	
	
	@Test
    public void getById() {
		assertNotNull(reservationRepository);
		
        Optional<Reservation> theReservation = reservationRepository.findById(1);
        
        assertNotNull(theReservation.get());
        assertEquals(theReservation.get().getId(), 1);
        assertEquals(theReservation.get().getUserName(), "Mario");
        assertEquals(theReservation.get().getUserSurname(), "Rossi");
    }
	
	@Test
	public void getByInvalidId() {
		assertNotNull(reservationRepository);
		
		Optional<Reservation> theReservation = reservationRepository.findById(0);
		
		assertTrue(!theReservation.isPresent());
	}
	
	@Test
	public void getByNotPresentId() {
		assertNotNull(reservationRepository);
		
		Optional<Reservation> theReservation = reservationRepository.findById(2000);
		
		assertTrue(!theReservation.isPresent());
	}
	
	@Test
	public void saveValidReservation() {
		Reservation theReservation = createReservation();
		
		Reservation theSavedReservation = reservationRepository.save(theReservation);
		
		assertEquals(theSavedReservation.getId(), theReservation.getId());
		assertEquals(theSavedReservation.getUserName(), theReservation.getUserName());
		assertEquals(theSavedReservation.getUserSurname(), theReservation.getUserSurname());
	}
	
	@Test(expected = org.springframework.dao.InvalidDataAccessApiUsageException.class)
	public void saveInvalidReservation() {
		assertNotNull(reservationRepository);
		
		reservationRepository.save(null);
	}
	
	
	private Reservation createReservation() {
    	Reservation theReservation = new Reservation();
    	theReservation.setId(1);
    	theReservation.setFlight(createFlight());
    	theReservation.setUserName("Mario");
    	theReservation.setUserSurname("Rossi");
    	theReservation.setPrice(105.76);
    	theReservation.setSeatsType("business");
    	theReservation.setSeatsNumber(2);
    	theReservation.setConfirmed(true);
    	
    	return theReservation;	
    }
	
	private Flight createFlight() {
    	Flight theFlight = new Flight();
    	
    	Company theCompany = new Company(1, "Ryanair");
        Country theSourceCountry = new Country(7, "Italy");
        City theSourceCity = new City(9, "Milan", theSourceCountry);
        Country theDestinationCountry = new Country(5, "United Kingdom");
        City theDestinationCity = new City(7, "London", theDestinationCountry);
        Airport theSourceAirport = new Airport(13, "Malpensa Airport", theSourceCity, "MPX");
        Airport theDestinationAirport = new Airport(9, "London Luton Airport", theDestinationCity, "LTN");
        theFlight.setId(1);
        theFlight.setCompany(theCompany);
        theFlight.setSourceAirport(theSourceAirport);
        theFlight.setSourceCity(theSourceCity);
        theFlight.setSourceCountry(theSourceCountry);
        theFlight.setDestinationAirport(theDestinationAirport);
        theFlight.setDestinationCity(theDestinationCity);
        theFlight.setDestinationCountry(theDestinationCountry);
        theFlight.setTotalEconomySeats(100);
        theFlight.setTotalBusinessSeats(60);
        theFlight.setTotalFirstSeats(35);
        theFlight.setAvailableEconomySeats(97);
        theFlight.setAvailableBusinessSeats(58);
        theFlight.setAvailableFirstSeats(35);
        theFlight.setEconomySeatPrice(17.99);
        theFlight.setBusinessSeatPrice(52.88);
        theFlight.setFirstSeatPrice(72.96);
        theFlight.setDepartureTime(java.sql.Timestamp.valueOf("2019-05-13 07:10:00"));
	    theFlight.setArrivalTime(java.sql.Timestamp.valueOf("2019-05-13 08:20:00"));
    	
	    return theFlight;
    }

}
