package com.oreilly.cloud.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="reservation")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reservation_id")
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="flight_id")
	private Flight flight;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="user_surname")
	private String userSurname;
	
	@Column(name="price")
	private double price;
	
	@Column(name="seats_type")
	private String seatsType;
	
	@Column(name="seats_number")
	private int seatsNumber;
	
	@Column(name="confirmed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean confirmed;
	
	
	public Reservation() {
		
	}

	public Reservation(Flight flight, String userName, String userSurname, double price, String seatsType,
			int seatsNumber, boolean confirmed) {
		
		this.flight = flight;
		this.userName = userName;
		this.userSurname = userSurname;
		this.price = price;
		this.seatsType = seatsType;
		this.seatsNumber = seatsNumber;
		this.confirmed = confirmed;
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

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", flight=" + flight + ", userName=" + userName + ", userSurname="
				+ userSurname + ", price=" + price + ", seatsType=" + seatsType + ", seatsNumber=" + seatsNumber
				+ ", confirmed=" + confirmed + "]";
	}

	
}
