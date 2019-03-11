package com.oreilly.cloud.object;

public class FlightReservationRequest {

	private int flightId;
	
	private String userEmail;
	
	private String seatClass;
	
	private int seatNumber;
	
	
	public FlightReservationRequest() {
		
	}

	public FlightReservationRequest(int flightId, String userEmail, String seatClass,
			int seatNumber) {
		
		this.flightId = flightId;
		this.userEmail = userEmail;
		this.seatClass = seatClass;
		this.seatNumber = seatNumber;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	@Override
	public String toString() {
		return "FlightReservationRequest [flightId=" + flightId + ", userEmail=" + userEmail + ", seatClass="
				+ seatClass + ", seatNumber=" + seatNumber + "]";
	}

	public String toJsonString() {
		String jsonString =
		"{" +
		"\"flightId\": " + flightId + ", " + 
		"\"userEmail\": " + "\"" + userEmail + "\"" + ", " +
		"\"seatClass\": " + "\"" + seatClass + "\"" + ", " +
		"\"seatNumber\": " + seatNumber + 
		"}";
		
		return jsonString;
	}
	
}
