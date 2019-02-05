package com.oreilly.cloud.model;

import java.time.LocalDateTime;

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
	@JoinColumn(name="room_id")
	private Room room;
	
	@Column(name="check_in")
	private LocalDateTime checkIn;
	
	@Column(name="check_out")
	private LocalDateTime checkOut;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="user_surname")
	private String userSurname;
	
	@Column(name="price")
	private double price;
	
	@Column(name="hosts_number")
	private int hostsNumber;
	
	@Column(name="reservation_type")
	private String reservationType;
	
	@Column(name="confirmed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean confirmed;

	
	public Reservation() {
		
	}
	
	public Reservation(Room room, LocalDateTime checkIn, LocalDateTime checkOut, String userName, String userSurname,
			double price, int hostsNumber, String reservationType, boolean confirmed) {
		
		this.room = room;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.userName = userName;
		this.userSurname = userSurname;
		this.price = price;
		this.hostsNumber = hostsNumber;
		this.reservationType = reservationType;
		this.confirmed = confirmed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDateTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDateTime checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDateTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDateTime checkOut) {
		this.checkOut = checkOut;
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

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", room=" + room + ", checkIn=" + checkIn + ", checkOut=" + checkOut
				+ ", userName=" + userName + ", userSurname=" + userSurname + ", price=" + price + ", hostsNumber="
				+ hostsNumber + ", reservationType=" + reservationType + ", confirmed=" + confirmed + "]";
	}

	
}
