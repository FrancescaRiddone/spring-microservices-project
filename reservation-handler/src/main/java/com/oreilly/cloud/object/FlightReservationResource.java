package com.oreilly.cloud.object;

import java.util.ArrayList;
import java.util.List;

public class FlightReservationResource {
	
	private int reservationId;
	
	private String userEmail;
	
	private FlightResource flight;
	
	private double price;
	
	private String seatsType;
	
	private int seatsNumber;
	
	private List<String> seats;
	
	
	public FlightReservationResource() {
		this.flight = new FlightResource();
		this.seats = new ArrayList<>();
	}

	public FlightReservationResource(int reservationId, String userEmail, FlightResource flight, double price,
			String seatsType, int seatsNumber) {
		this.reservationId = reservationId;
		this.userEmail = userEmail;
		this.flight = flight;
		this.price = price;
		this.seatsType = seatsType;
		this.seatsNumber = seatsNumber;
	}

	public FlightReservationResource(int reservationId, String userEmail, FlightResource flight, double price,
			String seatsType, int seatsNumber, List<String> seats) {
		this.reservationId = reservationId;
		this.userEmail = userEmail;
		this.flight = flight;
		this.price = price;
		this.seatsType = seatsType;
		this.seatsNumber = seatsNumber;
		this.seats = seats;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
	
	public List<String> getSeats() {
		return seats;
	}

	public void setSeats(List<String> seats) {
		this.seats = seats;
	}

	@Override
	public String toString() {
		String printString = "ReservationResource [reservationId=" + reservationId + ", userEmail=" + userEmail + ", flight=" + flight + ", price=" + price
				+ ", seatsType=" + seatsType + ", seatsNumber=" + seatsNumber + ", seatsId =";
		for(int i = 0; i < this.seats.size(); i++) {
			printString = printString.concat(this.seats.get(i) + " ");
		}
		printString = printString.concat("]");
		
		return printString;
	}


}
