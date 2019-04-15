package com.microservices.project.object;

public class BookingConfirmation {
	
	private int reservationId;
	
	private BankDetails bankDetails;

	
	public BookingConfirmation() {
		
	}
	
	public BookingConfirmation(int reservationId, BankDetails bankDetails) {
		this.reservationId = reservationId;
		this.bankDetails = bankDetails;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public BankDetails getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}

	@Override
	public String toString() {
		return "BookingConfirmation [reservationId=" + reservationId + ", bankDetails=" + bankDetails + "]";
	}
	

}
