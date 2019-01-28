package com.oreilly.cloud.object;

public class HotelReservationResource {
	
	private int reservationId;
	
	private RoomResource room;
	
	private double price;
	
	private String reservationType;
	
	private int hostsNumber;
	
	private CheckTime checkIn;
	
	private CheckTime checkOut;

	public HotelReservationResource() {
		
	}

	public HotelReservationResource(int reservationId, RoomResource room, double price, String reservationType,
			int hostsNumber, CheckTime checkIn, CheckTime checkOut) {
		
		this.reservationId = reservationId;
		this.room = room;
		this.price = price;
		this.reservationType = reservationType;
		this.hostsNumber = hostsNumber;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public RoomResource getRoom() {
		return room;
	}

	public void setRoom(RoomResource room) {
		this.room = room;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getReservationType() {
		return reservationType;
	}

	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}

	public int getHostsNumber() {
		return hostsNumber;
	}

	public void setHostsNumber(int hostsNumber) {
		this.hostsNumber = hostsNumber;
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
		return "HotelReservationResource [reservationId=" + reservationId + ", room=" + room + ", price=" + price
				+ ", reservationType=" + reservationType + ", hostsNumber=" + hostsNumber + ", checkIn=" + checkIn
				+ ", checkOut=" + checkOut + "]";
	}
	

}
