package com.oreilly.cloud.object;

public class FlightResource {
	
	private int flightId;
	
	private String company; 
	
	private JourneyStage source;
	
	private JourneyStage destination;
	
	private int availableEconomySeats;
	
	private int availableBusinessSeats;
	
	private int availableFirstSeats;
	
	private double economySeatPrice;
	
	private double businessSeatPrice;
	
	private double firstSeatPrice;
	
	
	public FlightResource() {
		this.company = "";
		this.source = new JourneyStage();
		this.destination = new JourneyStage();
	}

	public FlightResource(int flightId, String company, JourneyStage source, JourneyStage destination,
			int availableEconomySeats, int availableBusinessSeats, int availableFirstSeats, double economySeatPrice,
			double businessSeatPrice, double firstSeatPrice) {
		
		this.flightId = flightId;
		this.company = company;
		this.source = source;
		this.destination = destination;
		this.availableEconomySeats = availableEconomySeats;
		this.availableBusinessSeats = availableBusinessSeats;
		this.availableFirstSeats = availableFirstSeats;
		this.economySeatPrice = economySeatPrice;
		this.businessSeatPrice = businessSeatPrice;
		this.firstSeatPrice = firstSeatPrice;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public JourneyStage getSource() {
		return source;
	}

	public void setSource(JourneyStage source) {
		this.source = source;
	}

	public JourneyStage getDestination() {
		return destination;
	}

	public void setDestination(JourneyStage destination) {
		this.destination = destination;
	}

	public int getAvailableEconomySeats() {
		return availableEconomySeats;
	}

	public void setAvailableEconomySeats(int availableEconomySeats) {
		this.availableEconomySeats = availableEconomySeats;
	}

	public int getAvailableBusinessSeats() {
		return availableBusinessSeats;
	}

	public void setAvailableBusinessSeats(int availableBusinessSeats) {
		this.availableBusinessSeats = availableBusinessSeats;
	}

	public int getAvailableFirstSeats() {
		return availableFirstSeats;
	}

	public void setAvailableFirstSeats(int availableFirstSeats) {
		this.availableFirstSeats = availableFirstSeats;
	}

	public double getEconomySeatPrice() {
		return economySeatPrice;
	}

	public void setEconomySeatPrice(double economySeatPrice) {
		this.economySeatPrice = economySeatPrice;
	}

	public double getBusinessSeatPrice() {
		return businessSeatPrice;
	}

	public void setBusinessSeatPrice(double businessSeatPrice) {
		this.businessSeatPrice = businessSeatPrice;
	}

	public double getFirstSeatPrice() {
		return firstSeatPrice;
	}

	public void setFirstSeatPrice(double firstSeatPrice) {
		this.firstSeatPrice = firstSeatPrice;
	}

	@Override
	public String toString() {
		return "FlightResource [flightId=" + flightId + ", company=" + company + ", source=" + source + ", destination="
				+ destination + ", availableEconomySeats=" + availableEconomySeats + ", availableBusinessSeats="
				+ availableBusinessSeats + ", availableFirstSeats=" + availableFirstSeats + ", economySeatPrice="
				+ economySeatPrice + ", businessSeatPrice=" + businessSeatPrice + ", firstSeatPrice=" + firstSeatPrice
				+ "]";
	}
	

}
