package com.oreilly.cloud.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.oreilly.cloud.model.Flight;
import com.oreilly.cloud.model.QFlight;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightRepositoryTest {
	
	@Autowired
    private FlightRepository flightRepository;
	
	/*
	 * TESTS on method findById(int id) of FightRepository
	 */
	
	@Test
	public void getFlightByValidId() {
		assertNotNull(flightRepository);
		int flightId = 1;
		
        Optional<Flight> theFlight = flightRepository.findById(flightId);
        
        assertNotNull(theFlight.get());
        assertEquals(theFlight.get().getFlightId(), 1);
        assertEquals(theFlight.get().getSourceAirport().getName(), "Malpensa Airport");
        assertEquals(theFlight.get().getDestinationAirport().getName(), "London Luton Airport");
		assertEquals(theFlight.get().getDepartureTime(), LocalDateTime.of(2019, 5, 13, 7, 10));
	}
	
	@Test
	public void getFlightByInvalidId() {
		assertNotNull(flightRepository);
		int flightId = -1;
		
		Optional<Flight> theFlight = flightRepository.findById(flightId);
		
		assertTrue(!theFlight.isPresent());
	}
	
	@Test
	public void getFlightByIdNotFound() {
		assertNotNull(flightRepository);
		int flightId = 40000;
		
		Optional<Flight> theFlight = flightRepository.findById(flightId);
		
		assertTrue(!theFlight.isPresent());
	}
	
	/*
	 * TESTS on method findAll(Predicate predicate) of FightRepository
	 */
	
	@Test
	public void getFlightsFromAirportToAirportOnDepartureDate() {
		assertNotNull(flightRepository);
		Predicate predicate = createPredicate1();
		
		Iterable<Flight> flightsIterator = flightRepository.findAll(predicate);
		List<Flight> foundFlights = Lists.newArrayList(flightsIterator);
		
		assertNotNull(foundFlights);
		assertEquals(foundFlights.size(), 2);
		for(Flight foundFlight: foundFlights) {
			assertEquals(foundFlight.getCompany().getName(), "Ryanair");
			assertEquals(foundFlight.getSourceAirport().getName(), "Malpensa Airport");
			assertEquals(foundFlight.getDestinationAirport().getName(), "London Luton Airport");
			assertTrue(foundFlight.getDepartureTime().isAfter(LocalDateTime.of(2019, 5, 13, 7, 0)));
		}
	}
	
	@Test
	public void getFlightsNotFound() {
		assertNotNull(flightRepository);
		Predicate predicate = createPredicate2();
		
		Iterable<Flight> flightsIterator = flightRepository.findAll(predicate);
		List<Flight> foundFlights = Lists.newArrayList(flightsIterator);
		
		assertTrue(foundFlights.isEmpty());
	}
	
	/*
	 * TESTS on method findOne(Predicate predicate) of FightRepository
	 */
	
	@Test
	public void getFlightByPredicateFound() {
		assertNotNull(flightRepository);
		Predicate predicate = createPredicate3();
		
		Optional<Flight> theFlight = flightRepository.findOne(predicate);
		
		assertTrue(theFlight.isPresent());
		assertEquals(theFlight.get().getFlightId(), 1);
		assertTrue(theFlight.get().getAvailableEconomySeats() > 2);
	}
	
	@Test
	public void getFlightByPredicateNotFound() {
		assertNotNull(flightRepository);
		Predicate predicate = createPredicate4();
		
		Optional<Flight> theFlight = flightRepository.findOne(predicate);
		
		assertTrue(!theFlight.isPresent());
	}
	
	
	private Predicate createPredicate1() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		predicate.and(flight.sourceAirport.name.equalsIgnoreCase("Malpensa Airport"));
		predicate.and(flight.destinationAirport.name.equalsIgnoreCase("London Luton Airport"));
		predicate.and(flight.departureTime.between(LocalDateTime.of(2019, 5, 13, 7, 0), LocalDateTime.of(2019, 5, 13, 23, 59, 59)));
		
		return predicate;		
    }
	
	private Predicate createPredicate2() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		predicate.and(flight.sourceAirport.name.equalsIgnoreCase("Hartsfield–Jackson Atlanta International Airport"));
		predicate.and(flight.destinationAirport.name.equalsIgnoreCase("Fiumicino Airport"));
		predicate.and(flight.departureTime.between(LocalDateTime.of(2019, 5, 13, 7, 0), LocalDateTime.of(2019, 5, 13, 23, 59, 59)));
		
		return predicate;		
    }
	
	private Predicate createPredicate3() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		predicate.and(flight.flightId.eq(1));
		predicate.and(flight.availableEconomySeats.goe(2));
		
		return predicate;
    }
	
	private Predicate createPredicate4() {
    	BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		predicate.and(flight.flightId.eq(1));
		predicate.and(flight.availableEconomySeats.goe(200));
		
		return predicate;
    }
	

}
