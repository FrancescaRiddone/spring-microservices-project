package com.oreilly.cloud.service;

import com.oreilly.cloud.dao.FlightDAO;
import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.object.FlightResource;
import com.oreilly.cloud.object.JourneyStage;
import com.oreilly.cloud.object.SearchFlightRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    @InjectMocks
    private FlightServiceImpl flightService;

    @Mock
    private FlightDAO flightDAO;


    @Test
    public void flightFoundWithId() {
        // given
        // a flightService
        assertNotNull(flightService);


        // when
        // I search for a flight with id 1
        when(flightDAO.getFlight(1)).thenReturn(createFlight());

        FlightResource flight = flightService.getFlight(1);

        // then
        // I get the flight with some specification
        assertNotNull(flight);
        assertThat(flight.getFlightId(), is(1));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void flightNotFound() {
        // when
        // I search for a flight with id 1
        when(flightDAO.getFlight(0)).thenReturn(createFlight());
        flightService.getFlight(2);
    }


    @Test
    public void searchFlights() {

        // given a search request
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        JourneyStage source = new JourneyStage();
        source.setAirportName("Turin");
        JourneyStage destination = new JourneyStage();
        destination.setAirportName("London");
        
        // when I search for a flight with that request
        when(flightDAO.getFlights(any())).thenReturn(createFlights());
        List<FlightResource> flights = flightService.getFlights(searchFlightRequest);
        
        assertNotNull(flights);
        assertThat(flights.size(), is(1));
        
        
        //searchFlightRequest.setSourceAirport("Turin");
        //searchFlightRequest.setDestinationAirport("London");

        // when I search for a flight with that request
        //when(flightDAO.getFlights(any(),any(),any(),any(),any(),any(),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(),any(Integer.class))).thenReturn(createFlights());
        //JSONObject flights = flightService.getFlights(searchFlightRequest);

        // I get the correct flight
        //assertNotNull(flights);

       // assertThat(((List)flights.get("flights")).size(), is(1));


    }

    @Test(expected = ValidateException.class)
    public void searchFlightsValidateException() {

        // given a search request
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        
        // when I search for a flight with that request
        when(flightDAO.getFlights(any())).thenReturn(createFlights());
        flightService.getFlights(searchFlightRequest);
        
        
        // when I search for a flight with that request
        //when(flightDAO.getFlights(any(),any(),any(),any(),any(),any(),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(),any(Integer.class))).thenReturn(createFlights());
        //flightService.getFlights(searchFlightRequest);

    }

    private List<Flight> createFlights() {
        List<Flight>  flights = new ArrayList<>();
        flights.add(createFlight());

        return flights;
    }

    private Flight createFlight() {
    	Flight theFlight = new Flight();
    	theFlight.setId(1);

        return theFlight;

    }
}
