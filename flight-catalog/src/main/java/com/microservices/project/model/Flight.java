package com.microservices.project.model;

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

@Entity
@Table(name="flight")
public class Flight {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="flight_id")
	private int flightId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="company_id")
	private Company company;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="source_airport_id")
	private Airport sourceAirport;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="source_city_id")
	private City sourceCity;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="source_country_id")
	private Country sourceCountry;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="destination_airport_id")
	private Airport destinationAirport;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="destination_city_id")
	private City destinationCity;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="destination_country_id")
	private Country destinationCountry;
	
	@Column(name="total_economy_seats")
	private int totalEconomySeats;
	
	@Column(name="total_business_seats")
	private int totalBusinessSeats;
	
	@Column(name="total_first_seats")
	private int totalFirstSeats;
	
	@Column(name="available_economy_seats")
	private int availableEconomySeats;
	
	@Column(name="available_business_seats")
	private int availableBusinessSeats;
	
	@Column(name="available_first_seats")
	private int availableFirstSeats;
	
	@Column(name="economy_seat_price")
	private double economySeatPrice;
	
	@Column(name="business_seat_price")
	private double businessSeatPrice;
	
	@Column(name="first_seat_price")
	private double firstSeatPrice;
	
	@Column(name="departure_time")
	private LocalDateTime departureTime;
	
	@Column(name="arrival_time")
	private LocalDateTime arrivalTime;
	
	
	public Flight() {
		
	}

	public Flight(int flightId, Company company, Airport sourceAirport, City sourceCity, Country sourceCountry,
			Airport destinationAirport, City destinationCity, Country destinationCountry, int totalEconomySeats,
			int totalBusinessSeats, int totalFirstSeats, int availableEconomySeats, int availableBusinessSeats,
			int availableFirstSeats, double economySeatPrice, double businessSeatPrice, double firstSeatPrice,
			LocalDateTime departureTime, LocalDateTime arrivalTime) {
		
		this.flightId = flightId;
		this.company = company;
		this.sourceAirport = sourceAirport;
		this.sourceCity = sourceCity;
		this.sourceCountry = sourceCountry;
		this.destinationAirport = destinationAirport;
		this.destinationCity = destinationCity;
		this.destinationCountry = destinationCountry;
		this.totalEconomySeats = totalEconomySeats;
		this.totalBusinessSeats = totalBusinessSeats;
		this.totalFirstSeats = totalFirstSeats;
		this.availableEconomySeats = availableEconomySeats;
		this.availableBusinessSeats = availableBusinessSeats;
		this.availableFirstSeats = availableFirstSeats;
		this.economySeatPrice = economySeatPrice;
		this.businessSeatPrice = businessSeatPrice;
		this.firstSeatPrice = firstSeatPrice;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}

	public int getFlightId() {
		return flightId;
	}
	
	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public void setId(int flightId) {
		this.flightId = flightId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Airport getSourceAirport() {
		return sourceAirport;
	}

	public void setSourceAirport(Airport sourceAirport) {
		this.sourceAirport = sourceAirport;
	}

	public City getSourceCity() {
		return sourceCity;
	}

	public void setSourceCity(City sourceCity) {
		this.sourceCity = sourceCity;
	}

	public Country getSourceCountry() {
		return sourceCountry;
	}

	public void setSourceCountry(Country sourceCountry) {
		this.sourceCountry = sourceCountry;
	}

	public Airport getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public City getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(City destinationCity) {
		this.destinationCity = destinationCity;
	}

	public Country getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(Country destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	public int getTotalEconomySeats() {
		return totalEconomySeats;
	}

	public void setTotalEconomySeats(int totalEconomySeats) {
		this.totalEconomySeats = totalEconomySeats;
	}

	public int getTotalBusinessSeats() {
		return totalBusinessSeats;
	}

	public void setTotalBusinessSeats(int totalBusinessSeats) {
		this.totalBusinessSeats = totalBusinessSeats;
	}

	public int getTotalFirstSeats() {
		return totalFirstSeats;
	}

	public void setTotalFirstSeats(int totalFirstSeats) {
		this.totalFirstSeats = totalFirstSeats;
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

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Override
	public String toString() {
		return "Flight [flightId=" + flightId + ", company=" + company + ", sourceAirport=" + sourceAirport
				+ ", sourceCity=" + sourceCity + ", sourceCountry=" + sourceCountry + ", destinationAirport="
				+ destinationAirport + ", destinationCity=" + destinationCity + ", destinationCountry="
				+ destinationCountry + ", totalEconomySeats=" + totalEconomySeats + ", totalBusinessSeats="
				+ totalBusinessSeats + ", totalFirstSeats=" + totalFirstSeats + ", availableEconomySeats="
				+ availableEconomySeats + ", availableBusinessSeats=" + availableBusinessSeats
				+ ", availableFirstSeats=" + availableFirstSeats + ", economySeatPrice=" + economySeatPrice
				+ ", businessSeatPrice=" + businessSeatPrice + ", firstSeatPrice=" + firstSeatPrice + ", departureTime="
				+ departureTime + ", arrivalTime=" + arrivalTime + "]";
	}

	
}
