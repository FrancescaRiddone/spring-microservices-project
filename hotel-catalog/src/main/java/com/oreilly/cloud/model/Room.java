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
	
	@Column(name="single_beds")
	private int singleBeds;
	
	@Column(name="double_beds")
	private int doubleBeds;
	
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
	
	@Column(name="vault", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean vault;
	
	@Column(name="bathtub", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean bathtub;
	
	@Column(name="swimming_pool", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean swimmingPool;
	
	@Column(name="soundproofing", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean soundproofing;
	
	@Column(name="with_view", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean withView;
	
	@Column(name="bathroom", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean bathroom;
	
	@Column(name="balcony", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean balcony;
	
	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reservation> reservations;

	
	public Room() {
		reservations = new ArrayList<>();
	}

	public Room(int id, Hotel hotel, int hostsNumber, double standardDailyPrice, double withBreakfastDailyPrice,
			double halfBoardDailyPrice, double fullBoardDailyPrice, int singleBeds, int doubleBeds,
			boolean airConditioner, boolean heat, boolean tv, boolean telephone, boolean vault, boolean bathtub,
			boolean swimmingPool, boolean soundproofing, boolean withView, boolean bathroom, boolean balcony) {
		
		this.id = id;
		this.hotel = hotel;
		this.hostsNumber = hostsNumber;
		this.standardDailyPrice = standardDailyPrice;
		this.withBreakfastDailyPrice = withBreakfastDailyPrice;
		this.halfBoardDailyPrice = halfBoardDailyPrice;
		this.fullBoardDailyPrice = fullBoardDailyPrice;
		this.singleBeds = singleBeds;
		this.doubleBeds = doubleBeds;
		this.airConditioner = airConditioner;
		this.heat = heat;
		this.tv = tv;
		this.telephone = telephone;
		this.vault = vault;
		this.bathtub = bathtub;
		this.swimmingPool = swimmingPool;
		this.soundproofing = soundproofing;
		this.withView = withView;
		this.bathroom = bathroom;
		this.balcony = balcony;
		reservations = new ArrayList<>();
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

	public int getSingleBeds() {
		return singleBeds;
	}

	public void setSingleBeds(int singleBeds) {
		this.singleBeds = singleBeds;
	}

	public int getDoubleBeds() {
		return doubleBeds;
	}

	public void setDoubleBeds(int doubleBeds) {
		this.doubleBeds = doubleBeds;
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

	public boolean isVault() {
		return vault;
	}

	public void setVault(boolean vault) {
		this.vault = vault;
	}

	public boolean isBathtub() {
		return bathtub;
	}

	public void setBathtub(boolean bathtub) {
		this.bathtub = bathtub;
	}

	public boolean isSwimmingPool() {
		return swimmingPool;
	}

	public void setSwimmingPool(boolean swimmingPool) {
		this.swimmingPool = swimmingPool;
	}

	public boolean isSoundproofing() {
		return soundproofing;
	}

	public void setSoundproofing(boolean soundproofing) {
		this.soundproofing = soundproofing;
	}

	public boolean isWithView() {
		return withView;
	}

	public void setWithView(boolean withView) {
		this.withView = withView;
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

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", hotel=" + hotel + ", hostsNumber=" + hostsNumber + ", standardDailyPrice="
				+ standardDailyPrice + ", withBreakfastDailyPrice=" + withBreakfastDailyPrice + ", halfBoardDailyPrice="
				+ halfBoardDailyPrice + ", fullBoardDailyPrice=" + fullBoardDailyPrice + ", singleBeds=" + singleBeds
				+ ", doubleBeds=" + doubleBeds + ", airConditioner=" + airConditioner + ", heat=" + heat + ", tv=" + tv
				+ ", telephone=" + telephone + ", vault=" + vault + ", bathtub=" + bathtub + ", swimmingPool="
				+ swimmingPool + ", soundproofing=" + soundproofing + ", withView=" + withView + ", bathroom="
				+ bathroom + ", balcony=" + balcony + "]";
	}


}
