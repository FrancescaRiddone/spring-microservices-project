package com.oreilly.cloud.entity;

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
@Table(name="airport")
public class Airport {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="airport_id")
	private int id;
	
	@Column(name="airport")
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="city_id")
	private City city;
	
	@Column(name="code")
	private String code;
	
	@OneToMany(mappedBy = "sourceAirport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Flight> departingFlights;
	
	@OneToMany(mappedBy = "destinationAirport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Flight> incomingFlights;
	
	
	public Airport() {
		name = "";
		city = new City();
		code = "";
		departingFlights = new ArrayList<>();
		incomingFlights = new ArrayList<>();
	}

	public Airport(String name, City city, String code) {
		this.name = name;
		this.city = city;
		this.code = code;
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

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Airport [id=" + id + ", name=" + name + ", city=" + city + "]";
	}


}
