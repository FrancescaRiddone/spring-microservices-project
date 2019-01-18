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
		name = "";
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

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + "]";
	}


}
