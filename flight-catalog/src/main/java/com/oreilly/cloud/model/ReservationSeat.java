package com.oreilly.cloud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="reservation_seat")
public class ReservationSeat {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reservation_seat_id")
	private int id;
	
	@Column(name="seat_number")
	private String seatNumber;
	
	@ManyToOne
    @JoinColumn(name="reservation_id")
    private Reservation reservation;
	
	
	public ReservationSeat() {
		
	}
	
	public ReservationSeat(int id, String seatNumber, Reservation reservation) {
		this.id = id;
		this.seatNumber = seatNumber;
		this.reservation = reservation;
	}

	public ReservationSeat(String seatNumber, Reservation reservation) {
		this.seatNumber = seatNumber;
		this.reservation = reservation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	public String toString() {
		return "ReservationSeat [id=" + id + ", seatNumber=" + seatNumber  + "]";
	}
	
    
}
