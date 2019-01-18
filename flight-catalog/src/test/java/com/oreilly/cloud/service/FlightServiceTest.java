package com.oreilly.cloud.service;

import com.oreilly.cloud.dao.FlightDAO;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import org.json.simple.JSONObject;
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

        JSONObject flight = flightService.getFlight(1);

        // then
        // I get the flight with some specification
        assertNotNull(flight);
        assertThat(flight.size(), is(1));
        assertThat(flight.get("flightId"), is(1));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void flightNotFound() {
        // when
        // I search for a flight with id 1
        when(flightDAO.getFlight(1)).thenReturn(createFlight());
        flightService.getFlight(2);
    }


    @Test
    public void searchFlights() {

        // given a search request
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest();
        searchFlightRequest.setSourceAirport("Turin");
        searchFlightRequest.setDestinationAirport("London");

        // when I search for a flight with that request
        when(flightDAO.getFlights(any(),any(),any(),any(),any(),any(),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(),any(Integer.class))).thenReturn(createFlights());
        JSONObject flights = flightService.getFlights(searchFlightRequest);

        // I get the correct flight
        assertNotNull(flights);

        assertThat(((List)flights.get("flights")).size(), is(1));


    }

    @Test(expected = ValidateException.class)
    public void searchFlightsValidateException() {

        // given a search request
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest();

        // when I search for a flight with that request
        when(flightDAO.getFlights(any(),any(),any(),any(),any(),any(),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(),any(Integer.class))).thenReturn(createFlights());
        flightService.getFlights(searchFlightRequest);

    }

    private List<JSONObject> createFlights() {
        List<JSONObject>  flights = new ArrayList<>();
        flights.add(createFlight());

        return flights;
    }

    private JSONObject createFlight() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flightId", 1);

        return  jsonObject;

    }
}
