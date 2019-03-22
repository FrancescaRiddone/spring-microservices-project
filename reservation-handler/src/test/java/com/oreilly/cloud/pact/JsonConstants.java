package com.oreilly.cloud.pact;

public class JsonConstants {
	
	public static final String flightReservationWithId3 = 	"{\"reservationId\": 3,\"userEmail\": \"elisabianchi@gmail.com\", " +
																"\"flight\": {\"flightId\": 4, \"company\": \"easyJet\", " +
																	"\"source\": {\"airportName\": \"Orio al Serio Airport\",\"airportCode\": \"BGY\",\"city\": \"Milan\",\"country\": \"Italy\"}, " +
																	"\"destination\": {\"airportName\": \"London Gatwich Airport\",\"airportCode\": \"LGW\",\"city\": \"London\",\"country\": \"United Kingdom\"}, " +
																	"\"departure\": {\"minute\": 25,\"hour\": 16,\"day\": 13,\"month\": 5,\"year\": 2019}, " +
																	"\"arrival\": {\"minute\": 20,\"hour\": 17,\"day\": 13,\"month\": 5,\"year\": 2019}, " +
																	"\"availableEconomySeats\": 98, "  +
																	"\"availableBusinessSeats\": 56, " +
																	"\"availableFirstSeats\": 0, " +
																	"\"economySeatPrice\": 29.18, " +
																	"\"businessSeatPrice\": 48.67, " +
																	"\"firstSeatPrice\": 0.0}, " +
																"\"price\": 29.18," +
																"\"seatsType\": \"economy\", " +
																"\"seatsNumber\": 1, " +
																"\"seats\": [\"1E\"]}";

	public static final String listFlightReservationWithId3 = "[" + flightReservationWithId3 + "]";
	
	public static final String newValidFlightReservationRequest = 	"{\"flightId\":5," + 
																	"\"userEmail\":\"elisabianchi@gmail.com\"," + 
																	"\"seatClass\":\"economy\"," + 
																	"\"seatNumber\":2" + 
																	"}";
														
	public static final String hotelReservationWithId4 = 	"{\"reservationId\": 4," + 
															"\"userEmail\": \"mariorossi@yahoo.it\", " + 
															"\"room\": {" + 
																"\"roomId\": 14," + 
																"\"hotel\": \"Hilton London Bankside\"," + 
																"\"hostsNumber\": 2," + 
																"\"standardDailyPrice\": 400.0," + 
																"\"withBreakfastDailyPrice\": 450.0," + 
																"\"halfBoardDailyPrice\": 490.0," + 
																"\"fullBoardDailyPrice\": 550.0," + 
																"\"singleBeds\": 2," + 
																"\"doubleBeds\": 0," + 
																"\"airConditioner\": true," + 
																"\"heat\": true," + 
																"\"tv\": true," + 
																"\"telephone\": true," + 
																"\"bathtub\": true," + 
																"\"swimmingPool\": false," + 
																"\"soundproofing\": true," + 
																"\"withView\": true," + 
																"\"bathroom\": true," + 
																"\"balcony\": true," + 
																"\"vault\": true}," + 
															"\"price\": 4400.0," + 
															"\"reservationType\": \"full board\"," + 
															"\"hostsNumber\": 2," + 
															"\"checkIn\": {" + 
																"\"day\": 2," + 
																"\"month\": 8," + 
																"\"year\": 2019}," + 
															"\"checkOut\": {" + 
																"\"day\": 10," + 
																"\"month\": 8," + 
																"\"year\": 2019}" + 
															"}";
	
	public static final String listHotelReservationWithId4 = "[" + hotelReservationWithId4 + "]";
	
	public static final String newValidHotelReservationRequest = 	"{\"roomId\":5," + 
																	"\"userEmail\":\"mariorossi@yahoo.it\"," + 
																	"\"hostsNumber\":2," + 
																	"\"reservationType\":\"with breakfast\"," + 
																	"\"checkIn\":{" + 
																		"\"day\":26," + 
																		"\"month\":2," + 
																		"\"year\":2019" + 
																	"}," + 
																	"\"checkOut\":{" + 
																		"\"day\":28," + 
																		"\"month\":2," + 
																		"\"year\":2019" + 
																	"}" + 
																	"}";
	
	public static final String newHotelReservationInCart = 	"{\"reservationId\": 6," + 
															"\"userEmail\": \"mariorossi@yahoo.it\"," + 
															"\"room\": {" + 
																"\"roomId\": 5," + 
																"\"hotel\": \"Cheshire Hotel\"," + 
																"\"hostsNumber\": 2," + 
																"\"standardDailyPrice\": 161.0," + 
																"\"withBreakfastDailyPrice\": 176.0," + 
																"\"halfBoardDailyPrice\": 0.0," + 
																"\"fullBoardDailyPrice\": 0.0," + 
																"\"singleBeds\": 0," + 
																"\"doubleBeds\": 1," + 
																"\"airConditioner\": true," + 
																"\"heat\": true," + 
																"\"tv\": true," + 
																"\"telephone\": true," + 
																"\"bathtub\": true," + 
																"\"swimmingPool\": false," + 
																"\"soundproofing\": false," + 
																"\"withView\": false," + 
																"\"bathroom\": true," + 
																"\"balcony\": false," + 
																"\"vault\": false" + 
															"}," + 
															"\"price\": 352.0," + 
															"\"reservationType\": \"with breakfast\"," + 
															"\"hostsNumber\": 2," + 
															"\"checkIn\": {" + 
																"\"day\": 26," + 
																"\"month\": 2," + 
																"\"year\": 2019" + 
															"}," + 
															"\"checkOut\": {" + 
																"\"day\": 28," + 
																"\"month\": 2," + 
																"\"year\": 2019" + 
															"}" + 
															"}";

	
	
}
