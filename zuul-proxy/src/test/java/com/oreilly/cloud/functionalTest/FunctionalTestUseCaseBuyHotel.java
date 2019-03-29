package com.oreilly.cloud.functionalTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.oreilly.cloud.model.ApplicationUser;
import com.oreilly.cloud.object.BankDetails;
import com.oreilly.cloud.object.BirthDate;
import com.oreilly.cloud.object.BookingConfirmation;
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.HotelProperties;
import com.oreilly.cloud.object.HotelReservationRequest;
import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.object.HotelResource;
import com.oreilly.cloud.object.RoomProperties;
import com.oreilly.cloud.object.SearchHotelRequest;
import com.oreilly.cloud.object.UserCreationRequest;
import com.oreilly.cloud.object.UserResource;

import io.restassured.http.ContentType;

public class FunctionalTestUseCaseBuyHotel {
	
	@Test
	public void buyHotelUseCaseTest() {
		
		/*
		 * User sign up
		 */
		
		UserResource userResource = signUpStep();
		
		/*
		 * User login
		 */
		
		String token = loginStep(userResource);
		
		/*
		 * User looks for interesting hotels in London from 13/07/2019 to 15/07/2019
		 */
		
		HotelResource[] availableHotels = hotelsSearchStep();
		
		/*
		 * User adds the room with id 1 of the hotel with id 1 to cart
		 */
		
		addHotelRoomToCartStep(availableHotels[0].getAvailableRoomsIds().get(0), userResource.getUsername(), token);
		
		/*
		 * User looks at the hotels in his cart
		 */
		
		HotelReservationResource[] hotelsInCart = getHotelsInCartStep(token);
		
		/*
		 * User watches details of the hotel in which he is interested in
		 */
		
		HotelReservationResource selectedHotel = getHotelInCartStep(token, hotelsInCart[0].getReservationId());
		
		/*
		 * User buys the hotel in cart with reservation id 6
		 */
		
		buyHotelStep(token, selectedHotel.getReservationId());
		
	}
	
	
	private UserResource signUpStep() {
		UserCreationRequest userCreationRequest = new UserCreationRequest("mariagialli@yahoo.it", "yellow", "Maria", "Gialli", new BirthDate(5, 7, 1994));
		
		UserResource userResource = given().contentType("application/json;charset=UTF-8").body(userCreationRequest).
									when().accept("application/json").post("http://localhost:8080/signUp").
									as(UserResource.class); 
		
		System.out.println("REGISTERED USER: " + userResource);
		
		assertEquals(userResource.getUsername(), "mariagialli@yahoo.it");
		assertEquals(userResource.getName(), "Maria");
		assertEquals(userResource.getSurname(), "Gialli");
		
		return userResource;
	}
	
	private String loginStep(UserResource userResource) {
		ApplicationUser applicationUser = new ApplicationUser(userResource.getUsername(), "yellow");
		
		String token = 	given().accept("application/json;charset=UTF-8").body(applicationUser).
						when().post("http://localhost:8080/login").
						getHeader("Authorization");
		
		token = token.substring(7);
		
		System.out.println("OBTAINED AUTHORIZATION TOKEN: " + token);
		
		assertNotNull(token);
		
		return token;
	}
	
	private HotelResource[] hotelsSearchStep() {
		SearchHotelRequest hotelsRequest = createSearchHotelRequest();
		
		HotelResource[] availableHotels = given().contentType("application/json;charset=UTF-8").body(hotelsRequest).accept("application/json;charset=UTF-8").
											when().post("http://localhost:8080/hotel-catalog/hotels/requiredHotels").
											as(HotelResource[].class);
		
		System.out.println("AVAILABLE HOTELS IN London FROM 13/07/2019 TO 15/07/2019 :");
		for(HotelResource hotel: availableHotels) {
			System.out.println(hotel);
			
			assertEquals(hotel.getCity(), "London");
		}
		
		return availableHotels;
	}
	
	private void addHotelRoomToCartStep(int roomId, String username, String token) {
		HotelReservationRequest reservationRequest = new HotelReservationRequest(roomId, username, 2, "standard", 
																new CheckTime(13, 7, 2019), new CheckTime(15, 7, 2019));
		
		HotelReservationResource newHotelReservation = 	given().headers("Authorization",
	              															"Bearer " + token,
	              															"Content-Type", ContentType.JSON,
	              															"Accept", ContentType.JSON)
																.body(reservationRequest).
															when().post("http://localhost:8080/reservation-handler/cart/hotels/newHotel").
															as(HotelReservationResource.class);
		
		System.out.println("NEW HOTEL RESERVATION: " + newHotelReservation);
		
		assertEquals(newHotelReservation.getUserEmail(), username);
		assertEquals(newHotelReservation.getRoom().getRoomId(), 1);
		assertEquals(newHotelReservation.getHostsNumber(), 2);
	}
	
	private HotelReservationResource[] getHotelsInCartStep(String token) {
		HotelReservationResource[] hotelsInCart = given().headers("Authorization",
																	"Bearer " + token,
																	"Content-Type", ContentType.JSON,
																	"Accept", ContentType.JSON).
													when().get("http://localhost:8080/reservation-handler/cart/hotels").
													as(HotelReservationResource[].class);
		
		System.out.println("HOTELS IN CART:");
		for(HotelReservationResource hotel: hotelsInCart) {
			System.out.println(hotel);
		}
		
		assertEquals(hotelsInCart.length, 1);
		assertEquals(hotelsInCart[0].getRoom().getRoomId(), 1);
		assertEquals(hotelsInCart[0].getReservationId(), 6);
		
		return hotelsInCart;
	}
	
	private HotelReservationResource getHotelInCartStep(String token, int reservationId) {
		HotelReservationResource selectedHotel = 	given().headers("Authorization",
																	"Bearer " + token,
																	"Content-Type", ContentType.JSON,
																	"Accept", ContentType.JSON).
													when().get("http://localhost:8080/reservation-handler/cart/hotels/" + reservationId).
													as(HotelReservationResource.class);
		
		System.out.println("HOTEL USER IS INTERESTED IN: " + selectedHotel);
		
		assertEquals(selectedHotel.getUserEmail(), "mariagialli@yahoo.it");
		assertEquals(selectedHotel.getReservationId(), 6);
		assertEquals(selectedHotel.getRoom().getRoomId(), 1);
		
		return selectedHotel;
	}
	
	private void buyHotelStep(String token, int reservationId) {
		BookingConfirmation bookingConfirmation = new BookingConfirmation(reservationId, 
				new BankDetails("visa", "4625290368718596", "Maria Gialli", 5, 2021, 574));

		String buyHotelResult = given().headers("Authorization",
												"Bearer " + token,
												"Content-Type", ContentType.JSON,
												"Accept", ContentType.JSON).
										body(bookingConfirmation).
								when().post("http://localhost:8080/reservation-handler/cart/hotels/confirmation").asString();
		
		
		System.out.println("RESULT OF BUYING OPERATION OF HOTEL WITH RESERVATION ID " + reservationId + ": " + buyHotelResult);
		
		assertEquals(buyHotelResult, "Hotel reservation successfully confirmed.");
	}
	
	
	private SearchHotelRequest createSearchHotelRequest() {
    	SearchHotelRequest request = new SearchHotelRequest();
    	request.setCity("London");
    	request.setCheckIn(new CheckTime(13, 7, 2019));
    	request.setCheckOut(new CheckTime(15, 7, 2019));
    	request.setHostsNumber(2);
    	HotelProperties hotelProperties = new HotelProperties();
    	hotelProperties.setReservationType("standard");
    	request.setHotelProperties(hotelProperties);
    	RoomProperties roomProperties = new RoomProperties();
    	roomProperties.setMaxPrice(200.00);
    	request.setRoomProperties(roomProperties);
    	
    	return request;
    }


}
