package com.microservices.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="country")
public class Country {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="country_id")
	private int id;
	
	@Column(name="country")
	private String name;
	
	@OneToMany(mappedBy = "sourceCountry", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Flight> departingFlights;
	
	@OneToMany(mappedBy = "destinationCountry", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Flight> incomingFlights;
	
	
	public Country() {
		departingFlights = new ArrayList<>();
		incomingFlights = new ArrayList<>();
	}
	
	public Country(int id, String name) {
		this.id = id;
		this.name = name;
		departingFlights = new ArrayList<>();
		incomingFlights = new ArrayList<>();
	}

	public Country(String name) {
		this.name = name;
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
		return "Country [id=" + id + ", name=" + name + "]";
	}

	
}
