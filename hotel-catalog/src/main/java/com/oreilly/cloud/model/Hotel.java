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

import org.hibernate.annotations.Type;

@Entity
@Table(name="hotel")
public class Hotel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="hotel_id")
	private int id;
	
	@Column(name="hotel")
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="city_id")
	private City city;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="country_id")
	private Country country;
	
	@Column(name="address")
	private String address;
	
	@Column(name="stars")
	private int stars;
	
	@Column(name="wifi", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean wifi;
	
	@Column(name="parking", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean parking;
	
	@Column(name="restaurant", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean restaurant;
	
	@Column(name="for_disabled_people", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean forDisabledPeople;
	
	@Column(name="gym", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean gym;
	
	@Column(name="spa", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean spa;
	
	@Column(name="swimming_pool", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean swimmingPool;
	
	@Column(name="breakfast_available", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean breakfastAvailability;
	
	@Column(name="half_board_available", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean halfBoardAvailability;
	
	@Column(name="full_board_available", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean fullBoardAvailability;
	
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Room> rooms;

	
	public Hotel() {
		rooms = new ArrayList<>();
	}

	public Hotel(int id, String name, City city, Country country, String address, int stars, boolean wifi,
			boolean parking, boolean restaurant, boolean forDisabledPeople, boolean gym, boolean spa,
			boolean swimmingPool, boolean breakfastAvailability, boolean halfBoardAvailability,
			boolean fullBoardAvailability) {
		
		this.id = id;
		this.name = name;
		this.city = city;
		this.country = country;
		this.address = address;
		this.stars = stars;
		this.wifi = wifi;
		this.parking = parking;
		this.restaurant = restaurant;
		this.forDisabledPeople = forDisabledPeople;
		this.gym = gym;
		this.spa = spa;
		this.swimmingPool = swimmingPool;
		this.breakfastAvailability = breakfastAvailability;
		this.halfBoardAvailability = halfBoardAvailability;
		this.fullBoardAvailability = fullBoardAvailability;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public boolean isParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public boolean isRestaurant() {
		return restaurant;
	}

	public void setRestaurant(boolean restaurant) {
		this.restaurant = restaurant;
	}

	public boolean isForDisabledPeople() {
		return forDisabledPeople;
	}

	public void setForDisabledPeople(boolean forDisabledPeople) {
		this.forDisabledPeople = forDisabledPeople;
	}

	public boolean isGym() {
		return gym;
	}

	public void setGym(boolean gym) {
		this.gym = gym;
	}

	public boolean isSpa() {
		return spa;
	}

	public void setSpa(boolean spa) {
		this.spa = spa;
	}

	public boolean isSwimmingPool() {
		return swimmingPool;
	}

	public void setSwimmingPool(boolean swimmingPool) {
		this.swimmingPool = swimmingPool;
	}

	public boolean isBreakfastAvailability() {
		return breakfastAvailability;
	}

	public void setBreakfastAvailability(boolean breakfastAvailability) {
		this.breakfastAvailability = breakfastAvailability;
	}

	public boolean isHalfBoardAvailability() {
		return halfBoardAvailability;
	}

	public void setHalfBoardAvailability(boolean halfBoardAvailability) {
		this.halfBoardAvailability = halfBoardAvailability;
	}

	public boolean isFullBoardAvailability() {
		return fullBoardAvailability;
	}

	public void setFullBoardAvailability(boolean fullBoardAvailability) {
		this.fullBoardAvailability = fullBoardAvailability;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", city=" + city + ", country=" + country + ", address=" + address
				+ ", stars=" + stars + ", wifi=" + wifi + ", parking=" + parking + ", restaurant=" + restaurant
				+ ", forDisabledPeople=" + forDisabledPeople + ", gym=" + gym + ", spa=" + spa + ", swimmingPool="
				+ swimmingPool + ", breakfastAvailability=" + breakfastAvailability + ", halfBoardAvailability="
				+ halfBoardAvailability + ", fullBoardAvailability=" + fullBoardAvailability + "]";
	}

	
}
