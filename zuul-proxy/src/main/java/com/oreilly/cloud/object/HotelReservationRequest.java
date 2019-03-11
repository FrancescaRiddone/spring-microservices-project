package com.oreilly.cloud.object;

public class HotelReservationRequest {

	private int roomId;
	
	private String userEmail; 
	
	private int hostsNumber;
	
	private String reservationType;
	
	private CheckTime checkIn; 
	
	private CheckTime checkOut;
	
	
	public HotelReservationRequest() {
		
	}

	public HotelReservationRequest(int roomId, String userEmail, int hostsNumber,
			String reservationType, CheckTime checkIn, CheckTime checkOut) {
		
		this.roomId = roomId;
		this.userEmail = userEmail;
		this.hostsNumber = hostsNumber;
		this.reservationType = reservationType;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getHostsNumber() {
		return hostsNumber;
	}

	public void setHostsNumber(int hostsNumber) {
		this.hostsNumber = hostsNumber;
	}

	public String getReservationType() {
		return reservationType;
	}

	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}

	public CheckTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(CheckTime checkIn) {
		this.checkIn = checkIn;
	}

	public CheckTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(CheckTime checkOut) {
		this.checkOut = checkOut;
	}

	@Override
	public String toString() {
		return "HotelReservationRequest [roomId=" + roomId + ", userEmail=" + userEmail + ", hostsNumber=" + hostsNumber
				+ ", reservationType=" + reservationType + ", checkIn=" + checkIn + ", checkOut=" + checkOut + "]";
	}

	
}
