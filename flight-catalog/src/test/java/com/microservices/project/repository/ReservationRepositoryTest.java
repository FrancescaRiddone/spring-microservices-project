package com.microservices.project.repository;

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

import com.microservices.project.model.Airport;
import com.microservices.project.model.City;
import com.microservices.project.model.Company;
import com.microservices.project.model.Country;
import com.microservices.project.model.Flight;
import com.microservices.project.model.Reservation;
import com.microservices.project.model.ReservationSeat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationRepositoryTest {
	
	@Autowired
    private ReservationRepository reservationRepository;
	
	
	/*
	 * TESTS on method findById(int id) of ReservationRepository
	 */
	
	@Test
    public void getReservationByValidId() {
		assertNotNull(reservationRepository);
		int reservationId = 1;
		
        Optional<Reservation> theReservation = reservationRepository.findById(reservationId);
        
        assertNotNull(theReservation.get());
        assertEquals(theReservation.get().getId(), 1);
        assertEquals(theReservation.get().getUserEmail(), "mariorossi@yahoo.it");
    }
	
	@Test
	public void getReservationByInvalidId() {
		assertNotNull(reservationRepository);
		int reservationId = 0;
		
		Optional<Reservation> theReservation = reservationRepository.findById(reservationId);
		
		assertTrue(!theReservation.isPresent());
	}
	
	@Test
	public void getReservationNotFound() {
		assertNotNull(reservationRepository);
		int reservationId = 20000;
		
		Optional<Reservation> theReservation = reservationRepository.findById(reservationId);
		
		assertTrue(!theReservation.isPresent());
	}
	
	/*
	 * TESTS on method save(Reservation reservation) of ReservationRepository
	 */
	
	@Test
	public void saveValidReservation() {
		assertNotNull(reservationRepository);
		Reservation theReservation = createReservation();
		
		Reservation theSavedReservation = reservationRepository.save(theReservation);
		
		assertEquals(theSavedReservation.getId(), theReservation.getId());
		assertEquals(theSavedReservation.getUserEmail(), theReservation.getUserEmail());
	}
	
	
	private Reservation createReservation() {
    	Reservation theReservation = new Reservation();
    	theReservation.setId(1);
    	theReservation.setFlight(createFlight());
    	theReservation.setUserEmail("mariorossi@yahoo.it");
    	theReservation.setPrice(105.76);
    	theReservation.setSeatsType("business");
    	theReservation.setSeatsNumber(2);
    	theReservation.setConfirmed(true);
    	
    	List<ReservationSeat> seats = new ArrayList<>();
    	seats.add(new ReservationSeat("1A", theReservation));
    	seats.add(new ReservationSeat("2A", theReservation));
    	
    	theReservation.setReservationSeats(seats);
    	
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
        theFlight.setDepartureTime(LocalDateTime.of(2019, 5, 13, 7, 10));
	    theFlight.setArrivalTime(LocalDateTime.of(2019, 5, 13, 8, 20));
    	
	    return theFlight;
    }

}
