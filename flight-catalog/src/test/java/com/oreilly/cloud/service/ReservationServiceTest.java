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
import com.oreilly.cloud.model.Airport;
import com.oreilly.cloud.model.City;
import com.oreilly.cloud.model.Company;
import com.oreilly.cloud.model.Country;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.object.FlightReservationResource;
import com.oreilly.cloud.repository.ReservationRepository;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {
	
	@InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;
    
    
    @Test
    public void reservationFoundWithId() {
        assertNotNull(reservationService);

        when(reservationRepository.findById(1)).thenReturn(createOptionalReservation());

        Reservation theReservation = reservationService.getReservation(1);

        assertNotNull(theReservation);
        assertEquals(theReservation.getId(), 1);
    }
    
    @Test(expected = ValidateException.class)
    public void getReservationWithInvalidId() {
        assertNotNull(reservationService);

        reservationService.getReservation(0);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void reservationNotFound() {
        assertNotNull(reservationService);

        when(reservationRepository.findById(2000)).thenReturn(Optional.empty());

        reservationService.getReservation(2000);
    }
   
    /*
    @Test
    public void reservationResourceFoundWithId() {
    	int reservationId = 1;
    	
    	when(reservationService.getReservation(reservationId)).thenReturn(createReservation());
    	
    	FlightReservationResource reservationResource = reservationService.getReservationResource(reservationId);
    	
    	assertNotNull(reservationResource);
    	assertEquals(reservationResource.getReservationId(), reservationId);
    }
    */
    
    @Test(expected = ValidateException.class)
    public void getReservationResourceWithInvalidId() {
        assertNotNull(reservationService);
        
        reservationService.getReservationResource(0);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void reservationResourceNotFound() {
        assertNotNull(reservationService);

        reservationService.getReservationResource(2000);
    }
    
    @Test
    public void reservationSuccessfullySaved() {
    	Reservation theReservation = createReservation();
    	
    	when(reservationRepository.save(theReservation)).thenReturn(createReservation());
    	
    	FlightReservationResource theReservationResource = reservationService.saveReservation(theReservation);
    	
    	assertNotNull(theReservationResource);
    	assertEquals(theReservationResource.getReservationId(), 1);
    }
    
    @Test(expected = ValidateException.class)
    public void reservationWithInvalidIdNotSaved() {
    	Reservation theReservation = new Reservation();
    	theReservation.setId(0);
    	theReservation.setUserName("");
    	theReservation.setSeatsNumber(-1);
    	
    	reservationService.saveReservation(theReservation);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void reservationNotFoundNotSaved() {
    	Reservation theReservation = new Reservation();
    	theReservation.setId(2000);
    	theReservation.setFlight(createFlight());
    	theReservation.setUserName("Gino");
    	theReservation.setUserSurname("Antonellino");
    	theReservation.setSeatsNumber(2);
    	theReservation.setSeatsType("economy");
    	theReservation.setPrice(1000.00);
    	
    	reservationService.saveReservation(theReservation);
    }
    
    
    
    private Optional<Reservation> createOptionalReservation(){
    	Optional<Reservation> theOptReservation = Optional.of(createReservation());
    	
    	return theOptReservation;
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