package com.oreilly.cloud.service;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Airport;
import com.oreilly.cloud.model.City;
import com.oreilly.cloud.model.Company;
import com.oreilly.cloud.model.Country;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.JourneyStage;
import com.oreilly.cloud.object.SearchFlightRequest;
import com.oreilly.cloud.repository.FlightRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    @InjectMocks
    private FlightServiceImpl flightService;

    @Mock
    private FlightRepository flightRepository;


    @Test
    public void flightFoundWithId() {
        // given
        // a flightService
        assertNotNull(flightService);

        // when
        // I search for a flight with id 1
        when(flightRepository.findById(1)).thenReturn(createOptionalFlight());

        FlightResource flight = flightService.getFlight(1);

        // then
        // I get the flight with some specification
        assertNotNull(flight);
        assertThat(flight.getFlightId(), is(1));
        assertThat(flight.getCompany(), is("Ryanair"));
        assertThat(flight.getSource().getCity(), is("Milan"));
        assertThat(flight.getSource().getAirportName(), is("Malpensa Airport"));
        assertThat(flight.getDestination().getCity(), is("London"));
        assertThat(flight.getDestination().getAirportName(), is("London Luton Airport"));
    }
    
    @Test(expected = ValidateException.class)
    public void getFlightValidateException() {
        // when
        // I search for a flight with id 0
        
    	flightService.getFlight(0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void flightNotFound() {
        // when
        // I search for a flight with id which isn't in db
    	when(flightRepository.findById(100)).thenReturn(Optional.empty());
    	
    	// then
        flightService.getFlight(100);
    }

    
    @Test
    public void flightsFoundWithSourceAirportAndDestinationAirport() {
    	// given a search request
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        JourneyStage source = new JourneyStage();
        source.setAirportName("Malpensa Airport");
        JourneyStage destination = new JourneyStage();
        destination.setAirportName("London Luton Airport");
        
        // when I search for a flight with that request
        when(flightService.getFlightsByClass(searchFlightRequest)).thenReturn(createFlights(true));
        
        // then
        List<FlightResource> flights = flightService.getFlights(searchFlightRequest);
        
        assertNotNull(flights);
        assertThat(flights.size(), is(2));
        for(int i = 0; i < 2; i++) {
        	assertThat(flights.get(i).getSource().getCity(), is("Milan"));
        	assertThat(flights.get(i).getSource().getAirportName(), is("Malpensa Airport"));
            assertThat(flights.get(i).getDestination().getCity(), is("London"));
            assertThat(flights.get(i).getDestination().getAirportName(), is("London Luton Airport"));
        }
        
    }

    @Test(expected = ValidateException.class)
    public void getFlightsValidateException() {
    	// given a search of a flight with an empty request
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        
        // then
        flightService.getFlights(searchFlightRequest);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void getFlightsResourceNotFoundException() {
    	// given a search request
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        JourneyStage source = new JourneyStage();
        source.setCity("Atlanta");
        JourneyStage destination = new JourneyStage();
        destination.setCity("Rome");
        
        // when I search for a flight with that request
        when(flightService.getFlightsByClass(searchFlightRequest)).thenReturn(createFlights(false));
        
        // then
        flightService.getFlights(searchFlightRequest);
    }
    
    @Test
    public void availableFlightFound() {
    	// given parameters
        int flightId = 1;
        String seatClass = "economy";
        int seatNumber = 2;
        
        // when I check the availability of the flight with these properties
        when(flightService.checkFlightAvailabilityByClass(flightId, seatClass, seatNumber)).thenReturn(createFlight());
        
        // then
        Flight theFlight = flightService.checkFlightAvailability(flightId, seatClass, seatNumber);
        
        assertNotNull(theFlight);
        assertThat(theFlight.getSourceCity().getName(), is("Milan"));
        assertThat(theFlight.getDestinationCity().getName(), is("London"));
        assertTrue(theFlight.getAvailableEconomySeats() >= 2);
    }
    
    @Test
    public void availableFlightNotFound() {
    	// given parameters
        int flightId = 1;
        String seatClass = "first";
        int seatNumber = 50;
        
        // when I check the availability of the flight with these properties
        when(flightService.checkFlightAvailabilityByClass(flightId, seatClass, seatNumber)).thenReturn(null);
        
        // then
        Flight theFlight = flightService.checkFlightAvailability(flightId, seatClass, seatNumber);
        
        assertTrue(theFlight == null);
    }
    
    @Test(expected = ValidateException.class)
    public void checkFlightAvailabilityValidateException() {
    	// given invalid parameters
    	int flightId = 0;
        String seatClass = "";
        int seatNumber = 2;
        
        // then
        flightService.checkFlightAvailability(flightId, seatClass, seatNumber);
    }
 
    
    @Test(expected = ValidateException.class)
    public void updateAvailableSeatsValidateException() {
    	// given invalid parameters
    	int flightId = 0;
        String seatClass = "";
        int seatNumber = 2;
        
        // then
        flightService.updateAvailableSeats(flightId, seatClass, seatNumber);
    }
    
    
    @Test
    public void foundFlightWithEconomySeats() {
    	// given a search request
    	SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        JourneyStage source = new JourneyStage();
        source.setAirportName("Malpensa Airport");
        JourneyStage destination = new JourneyStage();
        destination.setAirportName("London Luton Airport");
        searchFlightRequest.setSeatType("economy");
        searchFlightRequest.setSeatNumber(2);
        
        // when I check the availability of the flight with these properties
        List<Flight> theFoudedFlights = new ArrayList<>();
        theFoudedFlights.add(createFlight());
        when(flightRepository.findBySourceDestinationSeatNumberAndEconomyAvailability(any(), any(), any(), any(), any(), any(), any(Integer.class))).thenReturn(theFoudedFlights);
        
        // then
        List<Flight> theFlights = flightService.getFlightsByClass(searchFlightRequest);
        
        assertNotNull(theFlights);
        assertThat(theFlights.size(), is(1));
        assertEquals(theFlights.get(0).getSourceAirport().getName(), "Malpensa Airport");
        assertEquals(theFlights.get(0).getDestinationAirport().getName(), "London Luton Airport");
        assertTrue(theFlights.get(0).getAvailableEconomySeats() >= 2);
    }
    
    @Test
    public void flightNotFoundWithEconomySeats() {
    	// given a search request
    	SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        JourneyStage source = new JourneyStage();
        source.setAirportName("Malpensa Airport");
        JourneyStage destination = new JourneyStage();
        destination.setAirportName("London Luton Airport");
        searchFlightRequest.setSeatType("economy");
        searchFlightRequest.setSeatNumber(200);
        
        // when I check the availability of the flight with these properties
        when(flightRepository.findBySourceDestinationSeatNumberAndEconomyAvailability(any(), any(), any(), any(), any(), any(), any(Integer.class))).thenReturn(null);
        
        // then
        List<Flight> theFlights = flightService.getFlightsByClass(searchFlightRequest);
        
        assertTrue(theFlights == null);
    }
    
    
    @Test
    public void availableFlightFoundWithBusinessSeats() {
    	// given parameters
    	int flightId = 1;
    	String seatClass = "business";
    	int seatNumber = 3;
    	
    	// when I check the availability of the flight with these properties
        when(flightRepository.findByIdSeatNumberAndBusinessAvailability(any(), any(Integer.class))).thenReturn(createFlight());
        
        // then
        Flight theFlight = flightService.checkFlightAvailabilityByClass(flightId, seatClass, seatNumber);
        
        assertNotNull(theFlight);
        assertEquals(theFlight.getSourceAirport().getName(), "Malpensa Airport");
        assertEquals(theFlight.getDestinationAirport().getName(), "London Luton Airport");
        assertTrue(theFlight.getAvailableBusinessSeats() >= 3);
    }
    
    @Test
    public void availableFlightWithBusinessSeatsNotFound() {
    	// given parameters
    	int flightId = 1;
    	String seatClass = "business";
    	int seatNumber = 100;
    	
    	// when I check the availability of the flight with these properties
        when(flightRepository.findByIdSeatNumberAndBusinessAvailability(any(), any(Integer.class))).thenReturn(null);
        
        // then
        Flight theFlight = flightService.checkFlightAvailabilityByClass(flightId, seatClass, seatNumber);
        
        assertTrue(theFlight == null);
    }
    
    
    private Optional<Flight> createOptionalFlight(){
    	Optional<Flight> optFlight;
    	optFlight = Optional.of(createFlight());	
    	return optFlight;
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
    
    private List<Flight> createFlights(boolean arePresent) {
    	if(!arePresent) {
    		return null;
    	}
    	
        List<Flight> theFlights = new ArrayList<>();
        
        Flight theFlight1 = createFlight();
        theFlights.add(theFlight1);
        
        Flight theFlight2 = new Flight();
        Company theCompany = new Company(1, "Ryanair");
        Country theSourceCountry = new Country(7, "Italy");
        City theSourceCity = new City(9, "Milan", theSourceCountry);
        Country theDestinationCountry = new Country(5, "United Kingdom");
        City theDestinationCity = new City(7, "London", theDestinationCountry);
        Airport theSourceAirport = new Airport(13, "Malpensa Airport", theSourceCity, "MPX");
        Airport theDestinationAirport = new Airport(9, "London Luton Airport", theDestinationCity, "LTN");
        theFlight2.setId(2);
        theFlight2.setCompany(theCompany);
        theFlight2.setSourceAirport(theSourceAirport);
        theFlight2.setSourceCity(theSourceCity);
        theFlight2.setSourceCountry(theSourceCountry);
        theFlight2.setDestinationAirport(theDestinationAirport);
        theFlight2.setDestinationCity(theDestinationCity);
        theFlight2.setDestinationCountry(theDestinationCountry);
        theFlight2.setTotalEconomySeats(100);
        theFlight2.setTotalBusinessSeats(60);
        theFlight2.setTotalFirstSeats(35);
        theFlight2.setAvailableEconomySeats(100);
        theFlight2.setAvailableBusinessSeats(60);
        theFlight2.setAvailableFirstSeats(35);
        theFlight2.setEconomySeatPrice(17.99);
        theFlight2.setBusinessSeatPrice(52.88);
        theFlight2.setFirstSeatPrice(72.96);
        theFlight2.setDepartureTime(java.sql.Timestamp.valueOf("2019-05-13 21:35:00"));
	    theFlight2.setArrivalTime(java.sql.Timestamp.valueOf("2019-05-13 22:30:00"));
        theFlights.add(theFlight2);
        
        return theFlights;
    }
    
}
