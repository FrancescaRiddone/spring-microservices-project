package com.microservices.project.functionalTest;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.microservices.project.model.ApplicationUser;
import com.microservices.project.object.BankDetails;
import com.microservices.project.object.BirthDate;
import com.microservices.project.object.BookingConfirmation;
import com.microservices.project.object.FlightReservationRequest;
import com.microservices.project.object.FlightReservationResource;
import com.microservices.project.object.FlightResource;
import com.microservices.project.object.FlightTime;
import com.microservices.project.object.JourneyStage;
import com.microservices.project.object.SearchFlightRequest;
import com.microservices.project.object.UserCreationRequest;
import com.microservices.project.object.UserResource;

public class FunctionalTestUseCaseBuyFlight {
	
	@Test
	public void buyFlightUseCaseTest() {
		
		/*
		 * User sign up
		 */
		
		UserResource userResource = signUpStep();
		
		/*
		 * User login
		 */
		
		String token = loginStep(userResource);
		
		/*
		 * User looks for interesting flights from Malpensa Airport to London Gatwich Airport on 13/05/2019 from 9:00 am
		 */
		
		FlightResource[] availableFlights = flightsSearchStep();
		
		/*
		 * User adds the flight with id 2 to cart
		 */
		
		addFlightToCartStep(availableFlights[0].getFlightId(), userResource.getUsername(), token);
		
		/*
		 * User looks at the flights in his cart
		 */
		
		FlightReservationResource[] flightsInCart = getFlightsInCartStep(token);
		
		/*
		 * User watches details of the flight in which he is interested in
		 */
		
		FlightReservationResource selectedFlight = getFlightInCartStep(token, flightsInCart[0].getReservationId());
		
		/*
		 * User buys the flight in cart with reservation id 5
		 */
		
		buyFlightStep(token, selectedFlight.getReservationId());	
	}
	
	
	private UserResource signUpStep() {
		UserCreationRequest userCreationRequest = new UserCreationRequest("giovannineri@yahoo.it", "secret", "Giovanni", "Neri", new BirthDate(5, 7, 1994));
		
		UserResource userResource = given().contentType("application/json;charset=UTF-8").body(userCreationRequest).
									when().accept("application/json").post("http://localhost:8080/signUp").
									as(UserResource.class); 
		
		System.out.println("REGISTERED USER: " + userResource);
		
		assertEquals(userResource.getUsername(), "giovannineri@yahoo.it");
		assertEquals(userResource.getName(), "Giovanni");
		assertEquals(userResource.getSurname(), "Neri");
		
		return userResource;
	}
	
	private String loginStep(UserResource userResource) {
		ApplicationUser applicationUser = new ApplicationUser(userResource.getUsername(), "secret");
		
		String token = 	given().accept("application/json;charset=UTF-8").body(applicationUser).
						when().post("http://localhost:8080/login").
						getHeader("Authorization");
		
		token = token.substring(7);
		
		System.out.println("OBTAINED AUTHORIZATION TOKEN: " + token);
		
		assertNotNull(token);
		
		return token;
	}
	
	private FlightResource[] flightsSearchStep() {
		SearchFlightRequest flightsRequest = createSearchFlightRequestWithAirports("Malpensa Airport", "London Gatwich Airport");
		
		FlightResource[] availableFlights = given().contentType("application/json;charset=UTF-8").body(flightsRequest).accept("application/json;charset=UTF-8").
											when().post("http://localhost:8080/flight-catalog/flights/requiredFlights").
											as(FlightResource[].class);
		
		System.out.println("AVAILABLE FLIGHTS FROM Malpensa Airport TO London Gatwich Airport ON 13/05/2019 FROM 9:00 am FOR 2 people IN economy:");
		for(FlightResource flight: availableFlights) {
			System.out.println(flight);
			
			assertEquals(flight.getSource().getAirportName(), "Malpensa Airport");
			assertEquals(flight.getDestination().getAirportName(), "London Gatwich Airport");
			assertTrue(flight.getDeparture().getHour() >= 9);
			assertEquals(flight.getDeparture().getDay(), 13);
			assertEquals(flight.getDeparture().getMonth(), 5);
			assertEquals(flight.getDeparture().getYear(), 2019);
		}
		
		return availableFlights;
	}
	
	private void addFlightToCartStep(int flightId, String username, String token) {
		FlightReservationRequest reservationRequest = new FlightReservationRequest(flightId, username, "economy", 2);
		
		FlightReservationResource newFlightReservation = 	given().headers("Authorization",
	              															"Bearer " + token,
	              															"Content-Type", ContentType.JSON,
	              															"Accept", ContentType.JSON)
																.body(reservationRequest).
															when().post("http://localhost:8080/reservation-handler/cart/flights/newFlight").
															as(FlightReservationResource.class);
		
		System.out.println("NEW FLIGHT RESERVATION: " + newFlightReservation);
		
		assertEquals(newFlightReservation.getUserEmail(), username);
		assertEquals(newFlightReservation.getFlight().getFlightId(), 5);
		assertEquals(newFlightReservation.getSeatsNumber(), 2);
		assertEquals(newFlightReservation.getSeatsType(), "economy");
	}
	
	private FlightReservationResource[] getFlightsInCartStep(String token) {
		FlightReservationResource[] flightsInCart = given().headers("Authorization",
																	"Bearer " + token,
																	"Content-Type", ContentType.JSON,
																	"Accept", ContentType.JSON).
													when().get("http://localhost:8080/reservation-handler/cart/flights").
													as(FlightReservationResource[].class);
		
		System.out.println("FLIGHTS IN CART:");
		for(FlightReservationResource flight: flightsInCart) {
			System.out.println(flight);
		}
		
		assertEquals(flightsInCart.length, 1);
		assertEquals(flightsInCart[0].getFlight().getFlightId(), 5);
		
		return flightsInCart;
	}
	
	private FlightReservationResource getFlightInCartStep(String token, int reservationId) {
		FlightReservationResource selectedFlight = 	given().headers("Authorization",
																	"Bearer " + token,
																	"Content-Type", ContentType.JSON,
																	"Accept", ContentType.JSON).
													when().get("http://localhost:8080/reservation-handler/cart/flights/" + reservationId).
													as(FlightReservationResource.class);
		
		System.out.println("FLIGHT USER IS INTERESTED IN: " + selectedFlight);
		
		assertEquals(selectedFlight.getUserEmail(), "giovannineri@yahoo.it");
		assertEquals(selectedFlight.getFlight().getFlightId(), reservationId);
		assertEquals(selectedFlight.getSeatsNumber(), 2);
		assertEquals(selectedFlight.getSeatsType(), "economy");
		
		return selectedFlight;
	}
	
	private void buyFlightStep(String token, int reservationId) {
		BookingConfirmation bookingConfirmation = new BookingConfirmation(reservationId, 
																	new BankDetails("visa", "4625290368718596", "Giovanni Neri", 5, 2021, 574));

		String buyFlightResult = 	given().headers("Authorization",
													"Bearer " + token,
													"Content-Type", ContentType.JSON,
													"Accept", ContentType.JSON).
											body(bookingConfirmation).
									when().post("http://localhost:8080/reservation-handler/cart/flights/confirmation").asString();
		
		
		System.out.println("RESULT OF BUYING OPERATION OF FLIGHT WITH RESERVATION ID " + reservationId + ": " + buyFlightResult);
		
		assertEquals(buyFlightResult, "Flight reservation successfully confirmed.");
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
	
	
}
