package com.oreilly.cloud.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void happyPath() throws Exception {

        String URI = "/flights/flight?flightId=1";

        mockMvc.perform(get(URI)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.flightId").value(1))
        		.andExpect(MockMvcResultMatchers.jsonPath("$.company").value("Ryanair"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void flightNotFOund() throws Exception {

        String URI = "/flights/flight?flightId=1000";

        mockMvc.perform(get(URI)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        ;
    }
    
    
 
}
