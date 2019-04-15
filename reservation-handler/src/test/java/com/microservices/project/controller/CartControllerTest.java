package com.microservices.project.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

import org.junit.runners.MethodSorters;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
	"eureka.client.enabled=false",
	"ribbon.eureka.enable=false",
	"flight-catalog.ribbon.listOfServers=flight-catalog",
	"hotel-catalog.ribbon.listOfServers=hotel-catalog"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class CartControllerTest {
	
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
			.get("/flights/reservations/reservation/3")
			.willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(JsonConstants.flightReservationWithId3)))
			.get("/flights/reservations/3")
			.willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(JsonConstants.flightReservationsInCartUserId2)))
			.post("/flights/reservations/new").body(JsonConstants.newValidFlightReservationRequest)
			.willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(JsonConstants.newFlightReservationInCart)))
			.post("/flights/reservations/new").body(JsonConstants.newInvalidFlightReservationRequest)
			.willReturn(ResponseCreators.success())
			.post("/flights/reservations/new").body(JsonConstants.newFlightReservationRequestForNotFoundFlight)
			.willReturn(ResponseCreators.serverError())
			.delete("/flights/reservations/reservation/5")
			.willReturn(ResponseCreators.success())
			.delete("/flights/reservations/reservation/-2")
			.willReturn(ResponseCreators.badRequest())
			.delete("/flights/reservations/reservation/10000")
			.willReturn(ResponseCreators.serverError())
			.put("/flights/reservations/confirmedReservation/4")
			.willReturn(ResponseCreators.success())
			.put("/flights/reservations/confirmedReservation/-2")
			.willReturn(ResponseCreators.success())
			.put("/flights/reservations/confirmedReservation/10000")
			.willReturn(ResponseCreators.serverError()),
		HoverflyDsl.service("http://hotel-catalog")
			.get("/hotels/reservations/reservation/4")
			.willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(JsonConstants.hotelReservationWithId4)))
			.get("/hotels/reservations/4")
			.willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(JsonConstants.hotelReservationsInCartUserId1)))
			.post("/hotels/reservations/new").body(JsonConstants.newValidHotelReservationRequest)
			.willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(JsonConstants.newHotelReservationInCart)))
			.post("/hotels/reservations/new").body(JsonConstants.newInvalidHotelReservationRequest)
			.willReturn(ResponseCreators.success())
			.post("/hotels/reservations/new").body(JsonConstants.newHotelReservationRequestForNotFoundRoom)
			.willReturn(ResponseCreators.serverError())
			.delete("/hotels/reservations/reservation/5")
			.willReturn(ResponseCreators.success())
			.delete("/hotels/reservations/reservation/-2")
			.willReturn(ResponseCreators.badRequest())
			.delete("/hotels/reservations/reservation/10000")
			.willReturn(ResponseCreators.serverError())
			.put("/hotels/reservations/confirmedReservation/5")
			.willReturn(ResponseCreators.success())
			.put("/hotels/reservations/confirmedReservation/-2")
			.willReturn(ResponseCreators.badRequest())
			.put("/hotels/reservations/confirmedReservation/10000")
			.willReturn(ResponseCreators.serverError())));
	
	/*
	 * TESTS on URI GET /cart/flights/{reservationId}
	 */
	
	@Test
	public void foundFlightReservationWithReservationIdAndUserId() throws Exception {
		String URI = "/cart/flights/3?userId=2";
		
		mockMvc.perform(get(URI))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.reservationId").value(3))
			.andExpect(MockMvcResultMatchers.jsonPath("$.flight.flightId").value(4))
			.andExpect(MockMvcResultMatchers.jsonPath("$.seatsType").value("economy"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.seatsNumber").value(1));
	}
	
	@Test
	public void flightReservationNotFoundWithInvalidReservationId() throws Exception {
		String URI = "/cart/flights/-1?userId=1";
		
		mockMvc.perform(get(URI))
			.andExpect(status().is(400));
	}

	/*
	 * TESTS on URI GET /cart/hotels/{reservationId}
	 */
	
	@Test
	public void foundHotelReservationWithReservationIdAndUserId() throws Exception {
		String URI = "/cart/hotels/4?userId=1";
		
		mockMvc.perform(get(URI))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.reservationId").value(4))
			.andExpect(MockMvcResultMatchers.jsonPath("$.room.roomId").value(14))
			.andExpect(MockMvcResultMatchers.jsonPath("$.room.hotel").value("Hilton London Bankside"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.reservationType").value("full board"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.hostsNumber").value(2));
	}
	
	@Test
	public void hotelReservationNotFoundWithInvalidReservationId() throws Exception {
		String URI = "/cart/hotels/-1?userId=1";
		
		mockMvc.perform(get(URI))
			.andExpect(status().is(400));
	}
	
	/*
	 * TESTS on URI GET /cart/flights
	 */
	
	@Test
	public void foundFlightReservationsWithUserId() throws Exception {
		String URI = "/cart/flights?userId=2";
		
		mockMvc.perform(get(URI))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].reservationId").value(3))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].flight.flightId").value(4))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsType").value("economy"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsNumber").value(1));
	}
	
	@Test
	public void flightReservationsNotFoundWithInvalidUserId() throws Exception {
		String URI = "/cart/flights?userId=-1";
		
		mockMvc.perform(get(URI))
			.andExpect(status().is(400));
	}
	
	/*
	 * TESTS on URI GET /cart/hotels
	 */
	
	@Test
	public void foundHotelReservationsWithUserId() throws Exception {
		String URI = "/cart/hotels?userId=2";
		
		mockMvc.perform(get(URI))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void hotelReservationsNotFoundWithInvalidUserId() throws Exception {
		String URI = "/cart/hotels?userId=-1";
		
		mockMvc.perform(get(URI))
			.andExpect(status().is(400));
	}
	
	/*
	 * TESTS on URI POST /cart/flights/newFlight
	 */
	
	@Test
	public void createNewFlightReservationWithValidFlightReservationRequest() throws Exception {
		String URI = "/cart/flights/newFlight?userId=1";
    	String requestJson = JsonConstants.newValidFlightReservationRequest;
		
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
	    	.andExpect(MockMvcResultMatchers.jsonPath("$.flight.flightId").value(5))
			.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(58.36))
			.andExpect(MockMvcResultMatchers.jsonPath("$.seatsType").value("economy"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.seatsNumber").value(2))
    		.andExpect(status().isOk());	
	}
	
	@Test
	public void newFlightReservationWithInvalidFlightReservationRequest() throws Exception {
		String URI = "/cart/flights/newFlight?userId=1";
    	String requestJson = JsonConstants.newInvalidFlightReservationRequest;
		
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
	    	.andExpect(status().is(400));	
	}
	
	@Test
	public void newFlightReservationWithFlightReservationRequestWithNotFoundFlight() throws Exception {
		String URI = "/cart/flights/newFlight?userId=1";
    	String requestJson = JsonConstants.newFlightReservationRequestForNotFoundFlight;
		
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
	    	.andExpect(status().is(404));	
	}
	
	/*
	 * TESTS on URI POST /cart/hotels/newHotel
	 */
	
	@Test
    public void createNewHotelReservationWithValidHotelReservationRequest() throws Exception {
    	String URI = "/cart/hotels/newHotel?userId=1";
    	String requestJson = JsonConstants.newValidHotelReservationRequest;
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.room.roomId").value(5))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(352))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.reservationType").value("with breakfast"))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.hostsNumber").value(2))
    			.andExpect(status().isOk());	
    }
	
	@Test
    public void newHotelReservationWithInvalidHotelReservationRequest() throws Exception {
    	String URI = "/cart/hotels/newHotel?userId=1";
    	String requestJson = JsonConstants.newInvalidHotelReservationRequest;
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(400));	
    }
	
	@Test
    public void newHotelReservationWithHotelReservationRequestWithNotFoundRoom() throws Exception {
    	String URI = "/cart/hotels/newHotel?userId=1";
    	String requestJson = JsonConstants.newHotelReservationRequestForNotFoundRoom;
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(404));	
    }
	
	/*
	 * TESTS on URI DELETE /cart/flights/{reservationId}
	 */
	
	@Test
    public void deletePresentFlightReservation() throws Exception {
    	String URI = "/cart/flights/5?userId=1";
    	
    	mockMvc.perform(delete(URI))
    			.andExpect(status().isOk());	
    }
	
	@Test
    public void deleteInvalidFlightReservation() throws Exception {
    	String URI = "/cart/flights/-2?userId=1";
    	
    	mockMvc.perform(delete(URI))
    			.andExpect(status().is(404));	
    }
	
	@Test
    public void deleteNotPresentFlightReservation() throws Exception {
    	String URI = "/cart/flights/10000?userId=1";
    	
    	mockMvc.perform(delete(URI))
    			.andExpect(status().is(404));	
    }
	
	/*
	 * TESTS on URI DELETE /cart/hotels/{reservationId}
	 */
	
	@Test
    public void deletePresentHotelReservation() throws Exception {
    	String URI = "/cart/hotels/5?userId=2";
    	
    	mockMvc.perform(delete(URI))
    			.andExpect(status().isOk());	
    }
	
	@Test
    public void deleteInvalidHotelReservation() throws Exception {
    	String URI = "/cart/hotels/-2?userId=2";
    	
    	mockMvc.perform(delete(URI))
    			.andExpect(status().is(404));	
    }
	
	@Test
    public void deleteNotPresentHotelReservation() throws Exception {
    	String URI = "/cart/hotels/10000?userId=2";
    	
    	mockMvc.perform(delete(URI))
    			.andExpect(status().is(404));	
    }
	
	/*
	 * TESTS on URI POST /cart/flights/confirmedFlight/{reservationId}
	 */
	
	@Test
    public void confirmPresentFlightReservation() throws Exception {
    	String URI = "/cart/flights/confirmation?userId=1";
    	String requestJson = JsonConstants.validBankDetails1a;
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().isOk())
    			.andExpect(MockMvcResultMatchers.content().string("Flight reservation successfully confirmed."));	
    }
	
	@Test
    public void notConfirmInvalidFlightReservation() throws Exception {
    	String URI = "/cart/flights/confirmation?userId=1";
    	String requestJson = JsonConstants.validBankDetails1b;
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(400));	
    }
	
	@Test
    public void notConfirmNotFoundFlightReservation() throws Exception {
    	String URI = "/cart/flights/confirmation?userId=1";
    	String requestJson = JsonConstants.validBankDetails1c;
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(404));	
    }
	
	/*
	 * TESTS on URI POST /cart/hotels/confirmedHotel/{reservationId}
	 */
	
	@Test
    public void confirmPresentHotelReservation() throws Exception {
    	String URI = "/cart/hotels/confirmation?userId=1";
    	String requestJson = JsonConstants.validBankDetails2a;
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().isOk())
    			.andExpect(MockMvcResultMatchers.content().string("Hotel reservation successfully confirmed."));	
    }
	
	@Test
    public void notConfirmInvalidHotelReservation() throws Exception {
    	String URI = "/cart/hotels/confirmation?userId=1";
    	String requestJson = JsonConstants.validBankDetails2b;
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(400));	
    }
	
	@Test
    public void notConfirmNotFoundHotelReservation() throws Exception {
    	String URI = "/cart/hotels/confirmation?userId=1";
    	String requestJson = JsonConstants.validBankDetails2c;
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(404));	
    }
	

}
