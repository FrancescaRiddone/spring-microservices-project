package com.oreilly.cloud.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="city")
public class City {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="city_id")
	private int id;

	@Column(name="city")
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="country_id")
	private Country country;
	
	@OneToMany(mappedBy = "sourceCity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Flight> departingFlights;
	
	@OneToMany(mappedBy = "destinationCity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Flight> incomingFlights;
	
	
	public City() {
		departingFlights = new ArrayList<>();
		incomingFlights = new ArrayList<>();
	}
	
	
	
	public City(int id, String name, Country country) {
		this.id = id;
		this.name = name;
		this.country = country;
		departingFlights = new ArrayList<>();
		incomingFlights = new ArrayList<>();
	}



	public City(String name, Country country) {
		this.name = name;
		this.country = country;
		departingFlights = new ArrayList<>();
		incomingFlights = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Country getCountry() {
		return country;
	}


	public void setCountry(Country country) {
		this.country = country;
	}
	
	public List<Flight> getDepartingFlights() {
		return departingFlights;
	}

	public void setDepartingFlights(List<Flight> departingFlights) {
		this.departingFlights = departingFlights;
	}

	public List<Flight> getIncomingFlights() {
		return incomingFlights;
	}

	public void setIncomingFlights(List<Flight> incomingFlights) {
		this.incomingFlights = incomingFlights;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", country=" + country + "]";
	}

}
