package com.microservices.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="user_reservation")
public class ReservationElement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_reservation_id")
	private int cartElementId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="reservation_id")
	private int reservationId;
	
	@Column(name="type")
	private String type;
	
	@Column(name="confirmed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean confirmed;

	
	public ReservationElement() {
	
	}

	public ReservationElement(int cartElementId, int userId, int reservationId, String type, boolean confirmed) {
		this.cartElementId = cartElementId;
		this.userId = userId;
		this.reservationId = reservationId;
		this.type = type;
		this.confirmed = confirmed;
	}
	
	public ReservationElement(int userId, int reservationId, String type, boolean confirmed) {
		this.userId = userId;
		this.reservationId = reservationId;
		this.type = type;
		this.confirmed = confirmed;
	}

	public int getCartElementId() {
		return cartElementId;
	}

	public void setCartElementId(int cartElementId) {
		this.cartElementId = cartElementId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	@Override
	public String toString() {
		return "CartElement [cartElementId=" + cartElementId + ", userId=" + userId + ", reservationId=" + reservationId
				+ ", type=" + type + ", confirmed=" + confirmed + "]";
	}
	
	
}
