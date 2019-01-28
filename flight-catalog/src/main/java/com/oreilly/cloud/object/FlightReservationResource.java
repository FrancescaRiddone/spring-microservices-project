package com.oreilly.cloud.object;

public class FlightReservationResource {
	
	private int reservationId;
	
	private FlightResource flight;
	
	private double price;
	
	private String seatsType;
	
	private int seatsNumber;
	
	
	public FlightReservationResource() {
		this.flight = new FlightResource();
		this.seatsType = "";
	}

	public FlightReservationResource(int reservationId, FlightResource flight, double price, String seatsType,
			int seatsNumber) {
		
		this.reservationId = reservationId;
		this.flight = flight;
		this.price = price;
		this.seatsType = seatsType;
		this.seatsNumber = seatsNumber;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public FlightResource getFlight() {
		return flight;
	}

	public void setFlight(FlightResource flight) {
		this.flight = flight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSeatsType() {
		return seatsType;
	}

	public void setSeatsType(String seatsType) {
		this.seatsType = seatsType;
	}

	public int getSeatsNumber() {
		return seatsNumber;
	}

	public void setSeatsNumber(int seatsNumber) {
		this.seatsNumber = seatsNumber;
	}

	@Override
	public String toString() {
		return "ReservationResource [reservationId=" + reservationId + ", flight=" + flight + ", price=" + price
				+ ", seatsType=" + seatsType + ", seatsNumber=" + seatsNumber + "]";
	}


}
