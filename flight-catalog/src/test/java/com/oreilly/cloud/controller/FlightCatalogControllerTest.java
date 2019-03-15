package com.oreilly.cloud.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.oreilly.cloud.object.FlightReservationRequest;
import com.oreilly.cloud.object.FlightTime;
import com.oreilly.cloud.object.JourneyStage;
import com.oreilly.cloud.object.SearchFlightRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightCatalogControllerTest {
	
	@Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    
    /*
	 * TESTS on URI /flights/flight/{flightId}
	 */

    @Test
    public void foundFlightWithId() throws Exception {
    	String URI = "/flights/flight/1";

        mockMvc.perform(get(URI))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.flightId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.company").value("Ryanair"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.source.airportName").value("Malpensa Airport"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.destination.airportName").value("London Luton Airport"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void flightNotFoundWithNotPresentId() throws Exception {
    	String URI = "/flights/flight/1000000";

        mockMvc.perform(get(URI))
                .andExpect(status().is(404));
    }
    
    @Test
    public void flightNotFoundWithInvalidId() throws Exception {
    	String URI = "/flights/flight/0";

        mockMvc.perform(get(URI))
                .andExpect(status().is(400));
    }
    
    /*
	 * TESTS on URI /flights/requiredFlights
	 */ 
    
    @Test
    public void foundFlightsWithSearchFlightRequest() throws Exception {
    	String URI = "/flights/requiredFlights";
    	SearchFlightRequest flightsRequest = createSearchFlightRequestWithAirports("Malpensa Airport", "London Luton Airport");
    	String requestJson = convertFlightRequestInJson(flightsRequest);
    	
        mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].flightId").value(2))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].company").value("Ryanair"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].source.airportName").value("Malpensa Airport"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].destination.airportName").value("London Luton Airport"))
        		.andExpect(status().isOk());
    }
    
    @Test
    public void flightsNotFoundWithValidSearchFlightRequest() throws Exception {
    	String URI = "/flights/requiredFlights";
    	SearchFlightRequest flightsRequest = createSearchFlightRequestWithAirports("Hartsfield–Jackson Atlanta International Airport", "Fiumicino Airport");
    	String requestJson = convertFlightRequestInJson(flightsRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(404));
    }
    
    @Test
    public void flightsNotFoundWithInvalidSearchFlightRequest() throws Exception {
    	String URI = "/flights/requiredFlights";
    	SearchFlightRequest flightsRequest = new SearchFlightRequest();
    	String requestJson = convertFlightRequestInJson(flightsRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(400));
    }
    
    /*
	 * TESTS on URI /flights/reservations/reservation/{reservationId}
	 */
    
    @Test
    public void foundReservationWithId() throws Exception {
    	String URI = "/flights/reservations/reservation/1";

        mockMvc.perform(get(URI)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.reservationId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.flight.flightId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.flight.company").value("Ryanair"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.flight.source.airportName").value("Malpensa Airport"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.flight.destination.airportName").value("London Luton Airport"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(105.76))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.seatsType").value("business"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.seatsNumber").value(2))
                .andExpect(status().isOk());
    }
    
    @Test
    public void reservationNotFoundWithValidId() throws Exception {
    	String URI = "/flights/reservations/reservation/1000000";

        mockMvc.perform(get(URI))
                .andExpect(status().is(404));
    }
    
    @Test
    public void reservationNotFoundWithInvalidId() throws Exception {
    	String URI = "/flights/reservations/reservation/0";

        mockMvc.perform(get(URI))
                .andExpect(status().is(400));
    }
    
    /*
	 * TESTS on URI /flights/reservations/{reservationIds}
	 */
    
    @Test
    public void foundReservationsWithIds() throws Exception {
    	String URI = "/flights/reservations/1, 2";
    	
        mockMvc.perform(get(URI))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].reservationId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].flight.flightId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(105.76))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsType").value("business"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[0].seatsNumber").value(2))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].reservationId").value(2))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].flight.flightId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(53.97))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].seatsType").value("economy"))
        		.andExpect(MockMvcResultMatchers.jsonPath("$[1].seatsNumber").value(3))
        		.andExpect(status().isOk());     
    }
    
    @Test
    public void reservationsNotFoundWithValidIds() throws Exception {
    	String URI = "/flights/reservations/1, 200000";
    	
        mockMvc.perform(get(URI))
        		.andExpect(status().is(404));     
    }
    
    @Test
    public void reservationsNotFoundWithInvalidIds() throws Exception {
    	String URI = "/flights/reservations/2, -1";
    	
        mockMvc.perform(get(URI))
        		.andExpect(status().is(400));     
    }
    
    /*
	 * TESTS on URI /flights/reservations/new
	 */
    
    @Test
    public void createReservationWithValidFlightReservationRequest() throws Exception {
    	String URI = "/flights/reservations/new";
    	FlightReservationRequest reservationRequest = createFlightReservationRequest("valid");
    	String requestJson = convertFlightReservationRequestInJson(reservationRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.flight.flightId").value(7))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(56.20))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.seatsType").value("economy"))
    			.andExpect(MockMvcResultMatchers.jsonPath("$.seatsNumber").value(2))
    			.andExpect(status().isOk());	
    }
    	
    @Test
    public void createReservationWithInvalidFlightReservationRequest() throws Exception {
    	String URI = "/flights/reservations/new";
    	FlightReservationRequest reservationRequest = createFlightReservationRequest("invalid");
    	String requestJson = convertFlightReservationRequestInJson(reservationRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(400));	
    }
    
    @Test
    public void createReservationWithNotFoundFlight() throws Exception {
    	String URI = "/flights/reservations/new";
    	FlightReservationRequest reservationRequest = createFlightReservationRequest("notFoundFlight");
    	String requestJson = convertFlightReservationRequestInJson(reservationRequest);
    	
    	mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
    			.andExpect(status().is(451));
    }
    
    /*
	 * TESTS on URI /flights/reservations/confirmedReservation/{reservationId}
	 */
    
    @Test
    public void confirmedReservationWithId() throws Exception {
    	String URI = "/flights/reservations/confirmedReservation/3";

        mockMvc.perform(put(URI)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(MockMvcResultMatchers.content().string("Flight reservation successfully confirmed."));
    }
    
    @Test
    public void notConfirmedReservationWithNotPresentId() throws Exception {
    	String URI = "/flights/reservations/confirmedReservation/100000";

        mockMvc.perform(put(URI)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(404));
    }
    
    @Test
    public void notConfirmedReservationWithInvalidId() throws Exception {
    	String URI = "/flights/reservations/confirmedReservation/0";

        mockMvc.perform(put(URI)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
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
    
    private String convertFlightRequestInJson(SearchFlightRequest flightsRequest) {
    	ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = "";
        
        try {
			requestJson = ow.writeValueAsString(flightsRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
        
        return requestJson;
    }
    
    private FlightReservationRequest createFlightReservationRequest(String typeOfRequest) {
    	FlightReservationRequest reservationRequest = new FlightReservationRequest();
    	if(typeOfRequest != null) {
	    	if(typeOfRequest.equals("valid")) {
	    		reservationRequest.setFlightId(7);
	        	reservationRequest.setUserEmail("vincenzoverdi@yahoo.it");
	        	reservationRequest.setSeatClass("economy");
	        	reservationRequest.setSeatNumber(2);
	    	} else if(typeOfRequest.equals("invalid")){
	    		reservationRequest.setFlightId(0);
	    		reservationRequest.setUserEmail("vincenzoverdi@yahoo.it");
	        	reservationRequest.setSeatClass("");
	        	reservationRequest.setSeatNumber(2);
	    	} else if(typeOfRequest.equals("notFoundFlight")){
	    		reservationRequest.setFlightId(100000);
	    		reservationRequest.setUserEmail("vincenzoverdi@yahoo.it");
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

 
}
