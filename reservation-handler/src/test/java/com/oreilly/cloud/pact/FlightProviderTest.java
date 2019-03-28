package com.oreilly.cloud.pact;

import static com.oreilly.cloud.pact.JsonConstants.flightReservationWithId3;
import static com.oreilly.cloud.pact.JsonConstants.listFlightReservationWithId3;
import static com.oreilly.cloud.pact.JsonConstants.newValidFlightReservationRequest;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.oreilly.cloud.object.FlightReservationResource;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class FlightProviderTest {
	
	@Rule
    public PactProviderRuleMk2 mockFlightProvider = new PactProviderRuleMk2("FlightCatalog", "localhost", 8112, this);


	@Pact(provider="FlightCatalog", consumer="ReservationHandler") 
    public RequestResponsePact createPact(PactDslWithProvider builder) {
		Map<String, String> headers = MapUtils.putAll(new HashMap<String, String>(),
		        new String[]{"Content-Type", "application/json;charset=UTF-8"});
		
		Map<String, String> headers1 = MapUtils.putAll(new HashMap<String, String>(),
		        new String[]{"Content-Type", "application/json"});
	    
	    return builder
	    		
	    	.given("test PUT")
	        	.uponReceiving("Test interaction in buyFlightInCart method")
	        	.path("/flights/reservations/confirmedReservation/4")
	        	.method("PUT")
	        .willRespondWith()
	            .status(200)
	            .body("Flight reservation successfully confirmed.")
	    		
            .given("test GET")
            .uponReceiving("Test interaction in getCartFlight method")
                .path("/flights/reservations/reservation/3")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body(flightReservationWithId3)
                .headers(headers)
                
            .given("test GET")
            .uponReceiving("Test interaction in getCartFlights method")
                .path("/flights/reservations/3")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body(listFlightReservationWithId3)
                .headers(headers)
            
            .given("test POST")
                .uponReceiving("Test interaction in addFlightToCart method")
                .method("POST")
                .body(newValidFlightReservationRequest)
                .path("/flights/reservations/new")
                .headers(headers1)
            .willRespondWith()
                .status(200)
                .headers(headers)
                .body(
            		new PactDslJsonBody()
                    .numberValue("reservationId", 5)
                    .stringValue("userEmail", "elisabianchi@gmail.com")
                    .stringValue("seatsType", "economy")
                    .numberValue("seatsNumber", 2)
                    .object("flight")
                    .numberValue("flightId", 5)
                    .closeObject()
                )
                
            .given("test DELETE")
                .uponReceiving("Test interaction in deleteFlightFromCart method")
                .method("DELETE")
                .path("/flights/reservations/reservation/5")
            .willRespondWith()
                .status(200)
                
            .toPact();
    }
	
	
	@Test
    @PactVerification("FlightCatalog")
    public void runTest() {
		System.setProperty("pact.rootDir","target/pacts");
		RestTemplate restTemplate = new RestTemplate();
		
		// Test for GET /flights/reservations/reservation/3
		
		FlightReservationResource flightReservation = restTemplate.getForObject(
				mockFlightProvider.getConfig().url() + "/flights/reservations/reservation/3", 
				FlightReservationResource.class);
		
		assertEquals(flightReservation.getReservationId(), 3);
        assertEquals(flightReservation.getUserEmail(), "elisabianchi@gmail.com");
        
        // Test for GET /flights/reservations/3
        
		ResponseEntity<List<FlightReservationResource>> flightReservations = restTemplate.exchange(
				mockFlightProvider.getConfig().url() + "/flights/reservations/3",
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<List<FlightReservationResource>>() {});
		
        assertEquals(flightReservations.getBody().size(), 1);
        assertEquals(flightReservations.getBody().get(0).getReservationId(), 3);
        assertEquals(flightReservations.getBody().get(0).getUserEmail(), "elisabianchi@gmail.com");
        
        // Test for POST /flights/reservations/new
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = newValidFlightReservationRequest;

        ResponseEntity<FlightReservationResource> createdFlightReservation = restTemplate.postForEntity(
        		mockFlightProvider.getConfig().url() + "/flights/reservations/new",
                new HttpEntity<>(requestBody, headers),
                FlightReservationResource.class);
        
        assertEquals(createdFlightReservation.getBody().getReservationId(), 5);
        assertEquals(createdFlightReservation.getBody().getUserEmail(), "elisabianchi@gmail.com");
        assertEquals(createdFlightReservation.getBody().getFlight().getFlightId(), 5);
        
        // Test for DELETE /flights/reservations/reservation/5
        
        restTemplate.delete(mockFlightProvider.getConfig().url() + "/flights/reservations/reservation/5");
        
        // Test for PUT /flights/reservations/confirmedReservation/4
        
        restTemplate.put(mockFlightProvider.getConfig().url() + "/flights/reservations/confirmedReservation/4", 
        		null, 
        		new HashMap<>());
        		
    }
	 

}
