package com.oreilly.cloud.pact;

import static org.junit.Assert.assertEquals;
import static com.oreilly.cloud.pact.JsonConstants.hotelReservationWithId4;
import static com.oreilly.cloud.pact.JsonConstants.listHotelReservationWithId4;
import static com.oreilly.cloud.pact.JsonConstants.newValidHotelReservationRequest;
import static com.oreilly.cloud.pact.JsonConstants.newHotelReservationInCart;

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

import com.oreilly.cloud.object.HotelReservationResource;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class TestHotelProvider {
	
	@Rule
    public PactProviderRuleMk2 mockHotelProvider = new PactProviderRuleMk2("HotelCatalog", "localhost", 8113, this);
	
	
	@Pact(provider="HotelCatalog", consumer="ReservationHandler") 
    public RequestResponsePact createPact(PactDslWithProvider builder) {
		Map<String, String> headers = MapUtils.putAll(new HashMap<String, String>(),
		        new String[]{"Content-Type", "application/json;charset=UTF-8"});
	    
		Map<String, String> headers1 = MapUtils.putAll(new HashMap<String, String>(),
		        new String[]{"Content-Type", "application/json"});
		
	    return builder
	    	
            .given("test GET")
            .uponReceiving("Test interaction in getCartHotel method")
                .path("/hotels/reservations/reservation/4")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body(hotelReservationWithId4)
                .headers(headers)
                
            .given("test GET")
            .uponReceiving("Test interaction in getCartHotels method")
	            .path("/hotels/reservations/4")
	            .method("GET")
	        .willRespondWith()
	            .status(200)
	            .body(listHotelReservationWithId4)
	            .headers(headers)
	            
	        .given("test POST")
		        .uponReceiving("Test interaction in addHotelToCart method")
		        .method("POST")
		        .body(newValidHotelReservationRequest)
		        .headers(headers1)
	            .path("/hotels/reservations/new")
	        .willRespondWith()
	            .status(200)
	            .headers(headers)
	            .body(newHotelReservationInCart)
	         
	        .given("test DELETE")
		        .uponReceiving("Test interaction in deleteHotelFromCart method")
		        .method("DELETE")
	            .path("/hotels/reservations/reservation/6")
	        .willRespondWith()
	            .status(200)
	        
            .toPact();
    }
	
	@Test
    @PactVerification("HotelCatalog")
    public void runTest() {
		System.setProperty("pact.rootDir","target/pacts");
		RestTemplate restTemplate = new RestTemplate();
		
		// Test for GET /hotels/reservations/reservation/4
		
		HotelReservationResource hotelReservation = restTemplate.getForObject(mockHotelProvider.getConfig().url() + "/hotels/reservations/reservation/4", HotelReservationResource.class);
		
		assertEquals(hotelReservation.getReservationId(), 4);
        assertEquals(hotelReservation.getUserEmail(), "mariorossi@yahoo.it");
        
    	// Test for GET /hotels/reservations/4
    
 		ResponseEntity<List<HotelReservationResource>> hotelReservations = restTemplate.exchange(
 				mockHotelProvider.getConfig().url() + "/hotels/reservations/4",
 				HttpMethod.GET, 
 				null, 
 				new ParameterizedTypeReference<List<HotelReservationResource>>() {});
 		
         assertEquals(hotelReservations.getBody().size(), 1);
         assertEquals(hotelReservations.getBody().get(0).getReservationId(), 4);
         assertEquals(hotelReservations.getBody().get(0).getUserEmail(), "mariorossi@yahoo.it");
       
         // Test for POST /hotels/reservations/new
        
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         String requestBody = newValidHotelReservationRequest;

         ResponseEntity<HotelReservationResource> createdHotelReservation = restTemplate.postForEntity(
         		mockHotelProvider.getConfig().url() + "/hotels/reservations/new",
                 new HttpEntity<>(requestBody, headers),
                 HotelReservationResource.class);
         
         assertEquals(createdHotelReservation.getBody().getReservationId(), 6);
         assertEquals(createdHotelReservation.getBody().getUserEmail(), "mariorossi@yahoo.it");
         assertEquals(createdHotelReservation.getBody().getRoom().getRoomId(), 5);
        
         // Test for DELETE /hotels/reservations/reservation/6
         
         restTemplate.delete(mockHotelProvider.getConfig().url() + "/hotels/reservations/reservation/6");
         
    }
	

}
