package com.oreilly.cloud.object;

public class FlightReservationRequest {

	private int flightId;
	
	private String userName;
	
	private String userSurname;
	
	private String seatClass;
	
	private int seatNumber;
	
	
	public FlightReservationRequest() {
		
	}

	public FlightReservationRequest(int flightId, String userName, String userSurname, String seatClass,
			int seatNumber) {
		
		this.flightId = flightId;
		this.userName = userName;
		this.userSurname = userSurname;
		this.seatClass = seatClass;
		this.seatNumber = seatNumber;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
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
		return "FlightReservationRequest [flightId=" + flightId + ", userName=" + userName + ", userSurname="
				+ userSurname + ", seatClass=" + seatClass + ", seatNumber=" + seatNumber + "]";
	}
	
	
}
