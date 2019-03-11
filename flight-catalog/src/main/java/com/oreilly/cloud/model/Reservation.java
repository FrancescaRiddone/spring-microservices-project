package com.oreilly.cloud.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="reservation")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reservation_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="flight_id")
	private Flight flight;
	
	@Column(name="user_email")
	private String userEmail;
	
	@Column(name="price")
	private double price;
	
	@Column(name="seats_type")
	private String seatsType;
	
	@Column(name="seats_number")
	private int seatsNumber;
	
	@Column(name="confirmed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean confirmed;
	
	@OneToMany(mappedBy = "reservation", orphanRemoval = true, cascade = CascadeType.PERSIST)
	private List<ReservationSeat> reservationSeats;
	
	
	public Reservation() {
		
	}
	
	public Reservation(int id, Flight flight, String userEmail, double price, String seatsType,
			int seatsNumber, boolean confirmed) {
		
		this.id = id;
		this.flight = flight;
		this.userEmail = userEmail;
		this.price = price;
		this.seatsType = seatsType;
		this.seatsNumber = seatsNumber;
		this.confirmed = confirmed;
		reservationSeats = new ArrayList<>();
	}

	public Reservation(Flight flight, String userEmail, double price, String seatsType,
			int seatsNumber, boolean confirmed) {
		
		this.flight = flight;
		this.userEmail = userEmail;
		this.price = price;
		this.seatsType = seatsType;
		this.seatsNumber = seatsNumber;
		this.confirmed = confirmed;
		reservationSeats = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	public List<ReservationSeat> getReservationSeats() {
		return reservationSeats;
	}

	public void setReservationSeats(List<ReservationSeat> reservationSeats) {
		this.reservationSeats = reservationSeats;
	}

	@Override
	public String toString() {
		String result = "Reservation [id=" + id + ", flight=" + flight + ", userEmail=" + userEmail +
				", price=" + price + ", seatsType=" + seatsType + ", seatsNumber=" + seatsNumber
				+ ", confirmed=" + confirmed + ", reservationSeats=";
		
		for(ReservationSeat r: reservationSeats) {
			result = result + r;
		}
		
		result = result + "]";
		
		return result;
	}
	
	
}
