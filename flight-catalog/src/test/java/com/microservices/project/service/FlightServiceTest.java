package com.microservices.project.service;

import com.microservices.project.exception.ResourceNotFoundException;
import com.microservices.project.exception.ResourceUnavailableException;
import com.microservices.project.exception.ValidateException;
import com.microservices.project.model.*;
import com.microservices.project.object.FlightResource;
import com.microservices.project.object.FlightTime;
import com.microservices.project.object.JourneyStage;
import com.microservices.project.object.SearchFlightRequest;
import com.microservices.project.repository.FlightRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    @InjectMocks
    private FlightServiceImpl flightService;

    @Mock
    private FlightRepository flightRepository;

    
    /*
	 * TESTS on method FlightResource getFlight(int flightId) of FlightService
	 */

    @Test
    public void flightFoundWithId() {
        assertNotNull(flightService);
        int flightId = 1;

        when(flightRepository.findById(flightId)).thenReturn(createOptionalFlight());
        FlightResource flight = flightService.getFlight(flightId);

        assertOnFlight(flight, 1);
    }

    @Test(expected = ValidateException.class)
    public void getFlightValidateException() {
    	assertNotNull(flightService);
    	int flightId = 0;
    	
        flightService.getFlight(flightId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void flightNotFound() {
    	assertNotNull(flightService);
    	int flightId = 100000;
        
    	when(flightRepository.findById(flightId)).thenReturn(Optional.empty());
    	flightService.getFlight(flightId);
    }
    
    /*
	 * TESTS on method List<FlightResource> getFlights(SearchFlightRequest searchFlightRequest) of FlightService
	 */

    @Test
    public void flightsFoundWithSourceAirportAndDestinationAirport() {
    	assertNotNull(flightService);
        SearchFlightRequest searchFlightRequest = createSearchFlightRequestWithAirports("Malpensa Airport", "London Luton Airport");

        when(flightRepository.findAll(createPredicate1())).thenReturn(createFlights(2));
        List<FlightResource> flights = flightService.getFlights(searchFlightRequest);
        
        assertNotNull(flights);
        assertThat(flights.size(), is(1));
        assertOnFlight(flights.get(0), 2);
        assertTrue(flights.get(0).getDeparture().getHour() >= 9);
    }

    @Test(expected = ValidateException.class)
    public void getFlightsValidateException() {
    	assertNotNull(flightService);
    	SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        
        flightService.getFlights(searchFlightRequest);
    }
 
    @Test(expected = ResourceNotFoundException.class)
    public void getFlightsResourceNotFoundException() {
    	assertNotNull(flightService);
    	SearchFlightRequest searchFlightRequest = createSearchFlightRequestWithAirports("Hartsfield–Jackson Atlanta International Airport", "Fiumicino Airport");
        
        flightService.getFlights(searchFlightRequest);
    }
    
    /*
	 * TESTS on method Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber) of FlightService
	 */
 
    @Test
    public void availableFlightFound() {
    	assertNotNull(flightService);
    	int flightId = 1;
        String seatClass = "economy";
        int seatNumber = 2;
        
        when(flightRepository.findOne(createPredicate2())).thenReturn(Optional.of(createFlight1()));
        Flight theFlight = flightService.checkFlightAvailability(flightId, seatClass, seatNumber);
        
        assertNotNull(theFlight);
        assertThat(theFlight.getSourceCity().getName(), is("Milan"));
        assertThat(theFlight.getDestinationCity().getName(), is("London"));
        assertTrue(theFlight.getAvailableEconomySeats() >= 2);
    }
    
    @Test(expected = ResourceUnavailableException.class)
    public void availableFlightNotFound() {
    	assertNotNull(flightService);
    	int flightId = 1;
        String seatClass = "first";
        int seatNumber = 50;
        
        when(flightRepository.findOne(createPredicate3())).thenReturn(Optional.empty());
        flightService.checkFlightAvailability(flightId, seatClass, seatNumber);
    }
   
    @Test(expected = ValidateException.class)
    public void checkFlightAvailabilityValidateException() {
    	assertNotNull(flightService);
    	int flightId = 0;
        String seatClass = "";
        int seatNumber = 2;
        
        flightService.checkFlightAvailability(flightId, seatClass, seatNumber);
    }
    
    /*
	 * TESTS on method void updateAvailableSeats(int flightId, String seatClass, int seatNumber) of FlightService
	 */
 
    @Test(expected = ValidateException.class)
    public void updateAvailableSeatsValidateException() {
    	assertNotNull(flightService);
    	int flightId = 0;
        String seatClass = "";
        int seatNumber = 2;
        
        flightService.updateAvailableSeats(flightId, seatClass, seatNumber);
    }
    
    
    private Optional<Flight> createOptionalFlight(){
    	return Optional.of(createFlight1());
    }
    
    private Flight createFlight1() {
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
    
    private Flight createFlight2() {
    	Flight theFlight = new Flight();
    	
    	Company theCompany = new Company(1, "Ryanair");
        Country theSourceCountry = new Country(7, "Italy");
        City theSourceCity = new City(9, "Milan", theSourceCountry);
        Country theDestinationCountry = new Country(5, "United Kingdom");
        City theDestinationCity = new City(7, "London", theDestinationCountry);
        Airport theSourceAirport = new Airport(13, "Malpensa Airport", theSourceCity, "MPX");
        Airport theDestinationAirport = new Airport(9, "London Luton Airport", theDestinationCity, "LTN");
        theFlight.setId(2);
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
        theFlight.setAvailableEconomySeats(100);
        theFlight.setAvailableBusinessSeats(60);
        theFlight.setAvailableFirstSeats(35);
        theFlight.setEconomySeatPrice(17.99);
        theFlight.setBusinessSeatPrice(52.88);
        theFlight.setFirstSeatPrice(72.96);
        theFlight.setDepartureTime(LocalDateTime.of(2019, 5, 13, 21, 35));
	    theFlight.setArrivalTime(LocalDateTime.of(2019, 5, 13, 22, 30));
    	
    	return theFlight;
    }
    
    private List<Flight> createFlights(int flightNumber) {
    	List<Flight> theFlights = new ArrayList<>();
        
    	if(flightNumber == 1) {
    		theFlights.add(createFlight1());
    	} else if(flightNumber == 2) {
    		theFlights.add(createFlight2());
    	} else {
    		theFlights.add(createFlight1());
    		theFlights.add(createFlight2());
    	}
        
        return theFlights;
    }
    
    private Predicate createPredicate1() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		predicate.and(flight.sourceAirport.name.equalsIgnoreCase("Malpensa Airport"));
		predicate.and(flight.destinationAirport.name.equalsIgnoreCase("London Luton Airport"));
		predicate.and(flight.availableEconomySeats.goe(2));	
		predicate.and(flight.departureTime.between(LocalDateTime.of(2019, 5, 13, 9, 0), LocalDateTime.of(2019, 5, 13, 23, 59, 59)));
		
		return predicate;		
    }
    
    private Predicate createPredicate2() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		predicate.and(flight.flightId.eq(1));
		predicate.and(flight.availableEconomySeats.goe(2));
		
		return predicate;
    }
    
    private Predicate createPredicate3() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		predicate.and(flight.flightId.eq(1));
		predicate.and(flight.availableFirstSeats.goe(50));
		
		return predicate;
    }
    
    private SearchFlightRequest createSearchFlightRequestWithAirports(String sourceAirport, String destinationAirport) {
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        JourneyStage source = new JourneyStage();
        source.setAirportName(sourceAirport);
        searchFlightRequest.setSource(source);
        JourneyStage destination = new JourneyStage();
        destination.setAirportName(destinationAirport);
        searchFlightRequest.setDestination(destination);
        searchFlightRequest.setSeatNumber(2);
        FlightTime time = new FlightTime(0, 9, 13, 5, 2019);
        searchFlightRequest.setDepartureTime(time);
        searchFlightRequest.setSeatType("economy");
        
        return searchFlightRequest;
    }

    private void assertOnFlight(FlightResource flight, int flightId) {
        assertNotNull(flight);
        assertEquals(flight.getFlightId(), flightId);
        assertThat(flight.getSource().getCity(), is("Milan"));
        assertThat(flight.getSource().getAirportName(), is("Malpensa Airport"));
        assertThat(flight.getDestination().getCity(), is("London"));
        assertThat(flight.getDestination().getAirportName(), is("London Luton Airport"));
    }
    

}
