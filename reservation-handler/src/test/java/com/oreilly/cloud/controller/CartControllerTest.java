package com.oreilly.cloud.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(properties = {
	"eureka.client.enabled=false",
	"ribbon.eureka.enable=false",
	"flight-catalog.ribbon.listOfServers=flight-catalog",
	"hotel-catalog.ribbon.listOfServers=hotel-catalog"
})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CartControllerTest {
	
	/*
	@Autowired
    protected WebApplicationContext wac;
	*/
	
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
    protected MockMvc mockMvc;
	
	
	
	
    
    //public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(9999));
    
	/*
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        WireMockRule wireMockRule = new WireMockRule();
    }
    */
    
    
    /*
	 * TESTS on URI /cart/flights/{reservationId}
	 * 
	 * FlightReservationResource getCartFlight(@PathVariable int reservationId, @RequestParam("userId") int userId)
	 * 
	 * si richiama URI /flights/reservations/reservation/1 in FLIGHT CATALOG
	 */
	
    @Test
    public void foundFlightReservationWithId() throws Exception {
    	String URI = "/cart/flights/1?userId=1";

        mockMvc.perform(get(URI))
                .andExpect(status().isOk());
    }
	
	
	
	
	
	
	

}
