package com.oreilly.cloud.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.oreilly.cloud.controller.JsonResponses.flightReservationWithId3;
import static com.oreilly.cloud.controller.JsonResponses.hotelReservationWithId4;
import static com.oreilly.cloud.controller.JsonResponses.flightReservationsInCartUserId2;
import static com.oreilly.cloud.controller.JsonResponses.hotelReservationsInCartUserId1;
import static com.oreilly.cloud.controller.JsonResponses.newFlightReservationInCart;
import static com.oreilly.cloud.controller.JsonResponses.newHotelReservationInCart;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.FlightReservationRequest;
import com.oreilly.cloud.object.HotelReservationRequest;

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
    
	@Autowired
	@Qualifier("restTemplateTest")
    RestTemplate restTemplate;

	
	
	@ClassRule
	public static HoverflyRule rule = HoverflyRule.inSimulationMode(SimulationSource.dsl(
			
			  HoverflyDsl.service("http://flight-catalog:80")
			  .get("/flights/reservations/reservation/3")
			  .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(flightReservationWithId3)))
			  .get("/flights/reservations/3")
			  .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(flightReservationsInCartUserId2)))
			  .post("/flights/reservations/new").anyBody()
			  .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(newFlightReservationInCart)))
			  
			  ,
			  
			  HoverflyDsl.service("http://hotel-catalog:80")
			  .get("/hotels/reservations/reservation/4")
			  .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(hotelReservationWithId4)))
			  .get("/hotels/reservations/4")
			  .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(hotelReservationsInCartUserId1)))
			  .post("/hotels/reservations/new").anyBody()
			  .willReturn(ResponseCreators.success(HttpBodyConverter.jsonWithSingleQuotes(newHotelReservationInCart)))
			  
			));
	
	
	/*
	 * TESTS on URI /cart/flights/{reservationId}
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
	 * TESTS on URI /cart/hotels/{reservationId}
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
	 * TESTS on URI /cart/flights
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
	 * TESTS on URI /cart/hotels
	 */
	
	@Test
	public void foundHotelReservationsWithUserId() throws Exception {
		String URI = "/cart/hotels?userId=1";
		
		mockMvc.perform(get(URI))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].reservationId").value(4))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].room.roomId").value(14))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].room.hotel").value("Hilton London Bankside"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].reservationType").value("full board"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].hostsNumber").value(2));
	}
	
	@Test
	public void hotelReservationsNotFoundWithInvalidUserId() throws Exception {
		String URI = "/cart/hotels?userId=-1";
		
		mockMvc.perform(get(URI))
			.andExpect(status().is(400));
	}
	
	/*
	 * TESTS on URI /cart/flights/newFlight
	 */
	
	@Test
	public void createNewFlightReservationWithValidFlightReservationRequest() throws Exception {
		String URI = "/cart/flights/newFlight?userId=1";
		FlightReservationRequest reservationRequest = createFlightReservationRequest("valid");
    	String requestJson = convertFlightReservationRequestInJson(reservationRequest);
		
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
	    	.andExpect(MockMvcResultMatchers.jsonPath("$.flight.flightId").value(5))
			.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(58.36))
			.andExpect(MockMvcResultMatchers.jsonPath("$.seatsType").value("economy"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.seatsNumber").value(2))
    		.andExpect(status().isOk());	
	}
	
	/*
	 * TESTS on URI /cart/hotels/newHotel
	 */
	
	@Test
    public void createNewHotelReservationWithValidHotelReservationRequest() throws Exception {
    	String URI = "/cart/hotels/newHotel?userId=2";
    	HotelReservationRequest reservationRequest = createHotelReservationRequest("valid");
    	String requestJson = convertHotelReservationRequestInJson(reservationRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.room.roomId").value(5))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(352))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.reservationType").value("with breakfast"))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.hostsNumber").value(2))
    			.andExpect(status().isOk());	
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private FlightReservationRequest createFlightReservationRequest(String typeOfRequest) {
    	FlightReservationRequest reservationRequest = new FlightReservationRequest();
    	if(typeOfRequest != null) {
	    	if(typeOfRequest.equals("valid")) {
	    		reservationRequest.setFlightId(5);
	        	reservationRequest.setUserEmail("mariorossi@yahoo.it");
	        	reservationRequest.setSeatClass("economy");
	        	reservationRequest.setSeatNumber(2);
	    	} else if(typeOfRequest.equals("invalid")){
	    		reservationRequest.setFlightId(0);
	    		reservationRequest.setUserEmail("mariorossi@yahoo.it");
	        	reservationRequest.setSeatClass("");
	        	reservationRequest.setSeatNumber(2);
	    	} else if(typeOfRequest.equals("notFoundFlight")){
	    		reservationRequest.setFlightId(100000);
	    		reservationRequest.setUserEmail("mariorossi@yahoo.it");
	        	reservationRequest.setSeatClass("business");
	        	reservationRequest.setSeatNumber(2);
	    	}
    	}
    	
        return reservationRequest;
    }
	
	private String convertFlightReservationRequestInJson(FlightReservationRequest reservationRequest) {
    	ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = "";
        
        try {
			requestJson = ow.writeValueAsString(reservationRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
        
        return requestJson;
    }
	
	private HotelReservationRequest createHotelReservationRequest(String typeOfRequest) {
    	HotelReservationRequest reservationRequest = new HotelReservationRequest();
    	if(typeOfRequest != null) {
	    	if(typeOfRequest.equals("valid")) {
	    		reservationRequest.setRoomId(5);
	    		reservationRequest.setUserEmail("elisabianchi@yahoo.it");
	    		reservationRequest.setHostsNumber(2);
	    		reservationRequest.setReservationType("with breakfast");
	    		CheckTime checkIn = new CheckTime(27, 3, 2019);
	    		CheckTime checkOut = new CheckTime(29, 3, 2019);
	    		reservationRequest.setCheckIn(checkIn);
	    		reservationRequest.setCheckOut(checkOut);
	    	} else if(typeOfRequest.equals("invalid")){
	    		reservationRequest.setRoomId(5);
	    		reservationRequest.setUserEmail("elisabianchi@yahoo.it");
	    		reservationRequest.setHostsNumber(4);
	    		reservationRequest.setReservationType("with breakfast");
	    		CheckTime checkIn = new CheckTime(27, 3, 2019);
	    		CheckTime checkOut = new CheckTime(29, 3, 2019);
	    		reservationRequest.setCheckIn(checkIn);
	    		reservationRequest.setCheckOut(checkOut);
	    	} else if(typeOfRequest.equals("notFound")){
	    		reservationRequest.setRoomId(10000);
	    		reservationRequest.setUserEmail("elisabianchi@yahoo.it");
	    		reservationRequest.setHostsNumber(2);
	    		reservationRequest.setReservationType("with breakfast");
	    		CheckTime checkIn = new CheckTime(27, 3, 2019);
	    		CheckTime checkOut = new CheckTime(29, 3, 2019);
	    		reservationRequest.setCheckIn(checkIn);
	    		reservationRequest.setCheckOut(checkOut);
	    	}
    	}
    	
        return reservationRequest;
    }
    
    private String convertHotelReservationRequestInJson(HotelReservationRequest reservationRequest) {
    	ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = "";
        
        try {
			requestJson = ow.writeValueAsString(reservationRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
        
        return requestJson;
    }
	

}
