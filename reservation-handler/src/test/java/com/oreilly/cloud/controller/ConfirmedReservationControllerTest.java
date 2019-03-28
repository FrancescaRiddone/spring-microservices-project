package com.oreilly.cloud.controller;

import static com.oreilly.cloud.controller.JsonConstants.flightReservationWithId2;
import static com.oreilly.cloud.controller.JsonConstants.hotelReservationWithId2;
import static com.oreilly.cloud.controller.JsonConstants.listWithFlightReservationWithId2;
import static com.oreilly.cloud.controller.JsonConstants.listWithHotelReservationWithId2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import io.specto.hoverfly.junit.dsl.HttpBodyConverter;
import io.specto.hoverfly.junit.dsl.ResponseCreators;
import io.specto.hoverfly.junit.rule.HoverflyRule;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
	"eureka.client.enabled=false",
	"ribbon.eureka.enable=false",
	"flight-catalog.ribbon.listOfServers=flight-catalog",
	"hotel-catalog.ribbon.listOfServers=hotel-catalog"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class ConfirmedReservationControllerTest {
	
	@Autowired
    protected WebApplicationContext wac;

	@Autowired
    protected MockMvc mockMvc;
    
    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
    	rule.resetJournal();
    }
    
	
	@ClassRule
	public static HoverflyRule rule = HoverflyRule.inSimulationMode(SimulationSource.dsl(
		HoverflyDsl.service("http://flight-catalog")
			.get("/flights/reservations/2")
			.willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(listWithFlightReservationWithId2)))
			.get("/flights/reservations/reservation/2")
			.willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(flightReservationWithId2)))
			,
			
		HoverflyDsl.service("http://hotel-catalog")
			.get("/hotels/reservations/2")
			.willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(listWithHotelReservationWithId2)))
			.get("/hotels/reservations/reservation/2")
			.willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(hotelReservationWithId2)))
			));
	
	/*
	 * TESTS on URI GET /registry/flights
	 */
	
	@Test
	public void foundFlightReservationsWithUserId() throws Exception {
		String URI = "/registry/flights?userId=2";
		
		mockMvc.perform(get(URI))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].reservationId").value(2))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].flight.flightId").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsType").value("economy"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsNumber").value(3));
	}
	
	@Test
	public void flightReservationsNotFoundWithInvalidUserId() throws Exception {
		String URI = "/registry/flights?userId=-1";
		
		mockMvc.perform(get(URI))
			.andExpect(status().is(400));
	}
	
	/*
	 * TESTS on URI GET /registry/hotels
	 */
	
	@Test
	public void foundHotelReservationsWithUserId() throws Exception {
		String URI = "/registry/hotels?userId=2";
		
		mockMvc.perform(get(URI))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].reservationId").value(2))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].room.roomId").value(4))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].room.hotel").value("Cheshire Hotel"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].reservationType").value("standard"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].hostsNumber").value(3));
	}
	
	@Test
	public void hotelReservationsNotFoundWithInvalidUserId() throws Exception {
		String URI = "/registry/hotels?userId=-1";
		
		mockMvc.perform(get(URI))
			.andExpect(status().is(400));
	}
	
	/*
	 * TESTS on URI GET /registry/flights/{reservationId}
	 */
	
	@Test
	public void foundFlightReservationWithReservationIdAndUserId() throws Exception {
		String URI = "/registry/flights/2?userId=2";
		
		mockMvc.perform(get(URI))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.reservationId").value(2))
			.andExpect(MockMvcResultMatchers.jsonPath("$.flight.flightId").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("$.seatsType").value("economy"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.seatsNumber").value(3));
	}
	
	@Test
	public void flightReservationNotFoundWithInvalidReservationId() throws Exception {
		String URI = "/registry/flights/-1?userId=1";
		
		mockMvc.perform(get(URI))
			.andExpect(status().is(400));
	}
	
	/*
	 * TESTS on URI GET /registry/hotels/{reservationId}
	 */
	
	@Test
	public void foundHotelReservationWithReservationIdAndUserId() throws Exception {
		String URI = "/registry/hotels/2?userId=2";
		
		mockMvc.perform(get(URI))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.reservationId").value(2))
			.andExpect(MockMvcResultMatchers.jsonPath("$.room.roomId").value(4))
			.andExpect(MockMvcResultMatchers.jsonPath("$.room.hotel").value("Cheshire Hotel"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.reservationType").value("standard"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.hostsNumber").value(3));
	}
	
	@Test
	public void hotelReservationNotFoundWithInvalidReservationId() throws Exception {
		String URI = "/registry/hotels/-1?userId=1";
		
		mockMvc.perform(get(URI))
			.andExpect(status().is(400));
	}
	

}
