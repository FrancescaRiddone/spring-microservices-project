package com.oreilly.cloud.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="room")
public class Room {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="room_id")
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="hotel_id")
	private Hotel hotel;
	
	@Column(name="hosts_number")
	private int hostsNumber;
	
	@Column(name="standard_daily_price")
	private double standardDailyPrice;
	
	@Column(name="with_breakfast_daily_price")
	private double withBreakfastDailyPrice;
	
	@Column(name="half_board_daily_price")
	private double halfBoardDailyPrice;
	
	@Column(name="full_board_daily_price")
	private double fullBoardDailyPrice;
	
	@Column(name="air_conditioner", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean airConditioner;
	
	@Column(name="heat", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean heat;
	
	@Column(name="TV", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean tv;
	
	@Column(name="telephone", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean telephone;
	
	@Column(name="wifi", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean wifi;
	
	@Column(name="towels", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean towels;
	
	@Column(name="hair_dryer", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean hairDryer;
	
	@Column(name="bathroom", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean bathroom;
	
	@Column(name="balcony", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean balcony;
	
	@Column(name="available", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean available;

	
	public Room() {
		hotel = new Hotel();
	}
	
	public Room(Hotel hotel, int hostsNumber, double standardDailyPrice, double withBreakfastDailyPrice,
			double halfBoardDailyPrice, double fullBoardDailyPrice, boolean airConditioner, boolean heat, boolean tv,
			boolean telephone, boolean wifi, boolean towels, boolean hairDryer, boolean bathroom, boolean balcony,
			boolean available) {
		
		this.hotel = hotel;
		this.hostsNumber = hostsNumber;
		this.standardDailyPrice = standardDailyPrice;
		this.withBreakfastDailyPrice = withBreakfastDailyPrice;
		this.halfBoardDailyPrice = halfBoardDailyPrice;
		this.fullBoardDailyPrice = fullBoardDailyPrice;
		this.airConditioner = airConditioner;
		this.heat = heat;
		this.tv = tv;
		this.telephone = telephone;
		this.wifi = wifi;
		this.towels = towels;
		this.hairDryer = hairDryer;
		this.bathroom = bathroom;
		this.balcony = balcony;
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public int getHostsNumber() {
		return hostsNumber;
	}

	public void setHostsNumber(int hostsNumber) {
		this.hostsNumber = hostsNumber;
	}

	public double getStandardDailyPrice() {
		return standardDailyPrice;
	}

	public void setStandardDailyPrice(double standardDailyPrice) {
		this.standardDailyPrice = standardDailyPrice;
	}

	public double getWithBreakfastDailyPrice() {
		return withBreakfastDailyPrice;
	}

	public void setWithBreakfastDailyPrice(double withBreakfastDailyPrice) {
		this.withBreakfastDailyPrice = withBreakfastDailyPrice;
	}

	public double getHalfBoardDailyPrice() {
		return halfBoardDailyPrice;
	}

	public void setHalfBoardDailyPrice(double halfBoardDailyPrice) {
		this.halfBoardDailyPrice = halfBoardDailyPrice;
	}

	public double getFullBoardDailyPrice() {
		return fullBoardDailyPrice;
	}

	public void setFullBoardDailyPrice(double fullBoardDailyPrice) {
		this.fullBoardDailyPrice = fullBoardDailyPrice;
	}

	public boolean isAirConditioner() {
		return airConditioner;
	}

	public void setAirConditioner(boolean airConditioner) {
		this.airConditioner = airConditioner;
	}

	public boolean isHeat() {
		return heat;
	}

	public void setHeat(boolean heat) {
		this.heat = heat;
	}

	public boolean isTv() {
		return tv;
	}

	public void setTv(boolean tv) {
		this.tv = tv;
	}

	public boolean isTelephone() {
		return telephone;
	}

	public void setTelephone(boolean telephone) {
		this.telephone = telephone;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public boolean isTowels() {
		return towels;
	}

	public void setTowels(boolean towels) {
		this.towels = towels;
	}

	public boolean isHairDryer() {
		return hairDryer;
	}

	public void setHairDryer(boolean hairDryer) {
		this.hairDryer = hairDryer;
	}

	public boolean isBathroom() {
		return bathroom;
	}

	public void setBathroom(boolean bathroom) {
		this.bathroom = bathroom;
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", hotel=" + hotel + ", hostsNumber=" + hostsNumber + ", standardDailyPrice="
				+ standardDailyPrice + ", withBreakfastDailyPrice=" + withBreakfastDailyPrice + ", halfBoardDailyPrice="
				+ halfBoardDailyPrice + ", fullBoardDailyPrice=" + fullBoardDailyPrice + ", airConditioner="
				+ airConditioner + ", heat=" + heat + ", tv=" + tv + ", telephone=" + telephone + ", wifi=" + wifi
				+ ", towels=" + towels + ", hairDryer=" + hairDryer + ", bathroom=" + bathroom + ", balcony=" + balcony
				+ ", available=" + available + "]";
	}

	
}
