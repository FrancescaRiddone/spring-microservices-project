package com.oreilly.cloud.service;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Airport;
import com.oreilly.cloud.model.City;
import com.oreilly.cloud.model.Company;
import com.oreilly.cloud.model.Country;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.model.QFlight;
import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.FlightTime;
import com.oreilly.cloud.object.JourneyStage;
import com.oreilly.cloud.object.SearchFlightRequest;
import com.oreilly.cloud.repository.FlightRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        assertNotNull(flightService);

        when(flightRepository.findById(1)).thenReturn(createOptionalFlight());
        FlightResource flight = flightService.getFlight(1);

        assertNotNull(flight);
        assertEquals(flight.getFlightId(), 1);
    }
    
    @Test(expected = ValidateException.class)
    public void getFlightValidateException() {
    	assertNotNull(flightService);
    	
        flightService.getFlight(0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void flightNotFound() {
    	assertNotNull(flightService);
        
    	when(flightRepository.findById(100)).thenReturn(Optional.empty());
    	flightService.getFlight(100);
    }

    
    @Test
    public void flightsFoundWithSourceAirportAndDestinationAirport() {
    	SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        JourneyStage source = new JourneyStage();
        source.setAirportName("Malpensa Airport");
        searchFlightRequest.setSource(source);
        JourneyStage destination = new JourneyStage();
        destination.setAirportName("London Luton Airport");
        searchFlightRequest.setDestination(destination);
        searchFlightRequest.setSeatNumber(2);
        FlightTime time = new FlightTime(0, 9, 13, 5, 2019);
        searchFlightRequest.setDepartureTime(time);
        
        when(flightRepository.findAll(createPredicate1())).thenReturn(createFlights(true, true));
        List<FlightResource> flights = flightService.getFlights(searchFlightRequest);
        
        assertNotNull(flights);
        assertThat(flights.size(), is(1));
        assertThat(flights.get(0).getSource().getCity(), is("Milan"));
        assertThat(flights.get(0).getSource().getAirportName(), is("Malpensa Airport"));
        assertThat(flights.get(0).getDestination().getCity(), is("London"));
        assertThat(flights.get(0).getDestination().getAirportName(), is("London Luton Airport"));
        assertTrue(flights.get(0).getDeparture().getHour() >= 9);
    }

    @Test(expected = ValidateException.class)
    public void getFlightsValidateException() {
    	SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        
        flightService.getFlights(searchFlightRequest);
    }
 
    @Test(expected = ResourceNotFoundException.class)
    public void getFlightsResourceNotFoundException() {
    	SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        JourneyStage source = new JourneyStage();
        source.setCity("Atlanta");
        searchFlightRequest.setSource(source);
        JourneyStage destination = new JourneyStage();
        destination.setCity("Rome");
        searchFlightRequest.setDestination(destination);
        FlightTime time = new FlightTime(0, 0, 13, 5, 2019);
        searchFlightRequest.setDepartureTime(time);
        
        flightService.getFlights(searchFlightRequest);
    }

    
    @Test
    public void availableFlightFound() {
    	int flightId = 1;
        String seatClass = "economy";
        int seatNumber = 2;
        
        when(flightRepository.findOne(createPredicate2())).thenReturn(Optional.of(createFlight()));
        Flight theFlight = flightService.checkFlightAvailability(flightId, seatClass, seatNumber);
        
        assertNotNull(theFlight);
        assertThat(theFlight.getSourceCity().getName(), is("Milan"));
        assertThat(theFlight.getDestinationCity().getName(), is("London"));
        assertTrue(theFlight.getAvailableEconomySeats() >= 2);
    }
    
    /*
    
    @Test
    public void availableFlightNotFound() {
    	int flightId = 1;
        String seatClass = "first";
        int seatNumber = 50;
        
        when(flightRepository.findOne(createPredicate3())).thenReturn(Optional.of(null));
        
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
    
    /*
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
    */
    
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
    
    private List<Flight> createFlights(boolean arePresent, boolean withDepartureFilter) {
    	if(!arePresent) {
    		return null;
    	}
    	
        List<Flight> theFlights = new ArrayList<>();
        
        Flight theFlight1 = new Flight();
        Company theCompany = new Company(1, "Ryanair");
        Country theSourceCountry = new Country(7, "Italy");
        City theSourceCity = new City(9, "Milan", theSourceCountry);
        Country theDestinationCountry = new Country(5, "United Kingdom");
        City theDestinationCity = new City(7, "London", theDestinationCountry);
        Airport theSourceAirport = new Airport(13, "Malpensa Airport", theSourceCity, "MPX");
        Airport theDestinationAirport = new Airport(9, "London Luton Airport", theDestinationCity, "LTN");
        theFlight1.setId(2);
        theFlight1.setCompany(theCompany);
        theFlight1.setSourceAirport(theSourceAirport);
        theFlight1.setSourceCity(theSourceCity);
        theFlight1.setSourceCountry(theSourceCountry);
        theFlight1.setDestinationAirport(theDestinationAirport);
        theFlight1.setDestinationCity(theDestinationCity);
        theFlight1.setDestinationCountry(theDestinationCountry);
        theFlight1.setTotalEconomySeats(100);
        theFlight1.setTotalBusinessSeats(60);
        theFlight1.setTotalFirstSeats(35);
        theFlight1.setAvailableEconomySeats(100);
        theFlight1.setAvailableBusinessSeats(60);
        theFlight1.setAvailableFirstSeats(35);
        theFlight1.setEconomySeatPrice(17.99);
        theFlight1.setBusinessSeatPrice(52.88);
        theFlight1.setFirstSeatPrice(72.96);
        theFlight1.setDepartureTime(java.sql.Timestamp.valueOf("2019-05-13 21:35:00"));
	    theFlight1.setArrivalTime(java.sql.Timestamp.valueOf("2019-05-13 22:30:00"));
	    
	    theFlights.add(theFlight1);
        if(!withDepartureFilter) {
        	Flight theFlight2 = createFlight();
        	theFlights.add(theFlight2);
        }
        
        return theFlights;
    }
    
    private Predicate createPredicate1() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		predicate.and(flight.sourceAirport.name.equalsIgnoreCase("Malpensa Airport"));
		predicate.and(flight.destinationAirport.name.equalsIgnoreCase("London Luton Airport"));
		predicate.and((flight.availableEconomySeats.add(flight.availableBusinessSeats).add(flight.availableFirstSeats)).goe(2));	
		
		try {
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String dateString = "2019-05-13 09:00:00.0";
			Date departureDate = formatter.parse(dateString);
			Timestamp departureTimestamp = new Timestamp(departureDate.getTime());
			Date departureLimit = formatter.parse(dateString.substring(0, 11).concat("23:59:59"));
			Timestamp departureLimitTimestamp = new Timestamp(departureLimit.getTime());
			predicate.and(flight.departureTime.between(departureTimestamp, departureLimitTimestamp));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
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
    
    
}
