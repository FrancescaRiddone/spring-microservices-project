package com.microservices.project.object;

import java.util.ArrayList;
import java.util.List;

public class HotelResource {
	
	private int hotelId;
	
	private String hotelName;
	
	private String city;
	
	private String country;
	
	private String address;
	
	private int stars;
	
	private boolean wifi;
	
	private boolean parking;
	
	private boolean restaurant;
	
	private boolean forDisabledPeople;
	
	private boolean gym;
	
	private boolean spa;
	
	private boolean swimmingPool;
	
	private boolean breakfastAvailable;
	
	private boolean halfBoardAvailable;
	
	private boolean fullBoardAvailable;
	
	private List<Integer> availableRoomsIds;
	
	
	public HotelResource() {
		availableRoomsIds = new ArrayList<>();
	}


	public HotelResource(int hotelId, String hotelName, String city, String country, String address, int stars,
			boolean wifi, boolean parking, boolean restaurant, boolean forDisabledPeople, boolean gym, boolean spa,
			boolean swimmingPool, boolean breakfastAvailable, boolean halfBoardAvailable, boolean fullBoardAvailable) {
		
		this.hotelId = hotelId;
		this.hotelName = hotelName;
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
		this.breakfastAvailable = breakfastAvailable;
		this.halfBoardAvailable = halfBoardAvailable;
		this.fullBoardAvailable = fullBoardAvailable;
		availableRoomsIds = new ArrayList<>();
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
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

	public boolean isBreakfastAvailable() {
		return breakfastAvailable;
	}

	public void setBreakfastAvailable(boolean breakfastAvailable) {
		this.breakfastAvailable = breakfastAvailable;
	}

	public boolean isHalfBoardAvailable() {
		return halfBoardAvailable;
	}

	public void setHalfBoardAvailable(boolean halfBoardAvailable) {
		this.halfBoardAvailable = halfBoardAvailable;
	}

	public boolean isFullBoardAvailable() {
		return fullBoardAvailable;
	}

	public void setFullBoardAvailable(boolean fullBoardAvailable) {
		this.fullBoardAvailable = fullBoardAvailable;
	}

	public List<Integer> getAvailableRoomsIds() {
		return availableRoomsIds;
	}


	public void setAvailableRoomsIds(List<Integer> availableRoomsIds) {
		this.availableRoomsIds = availableRoomsIds;
	}

	@Override
	public String toString() {
		String hotelResource = "HotelResource [hotelId=" + hotelId + ", hotelName=" + hotelName + ", city=" + city + ", country="
				+ country + ", address=" + address + ", stars=" + stars + ", wifi=" + wifi + ", parking=" + parking
				+ ", restaurant=" + restaurant + ", forDisabledPeople=" + forDisabledPeople + ", gym=" + gym + ", spa="
				+ spa + ", swimmingPool=" + swimmingPool + ", breakfastAvailable=" + breakfastAvailable
				+ ", halfBoardAvailable=" + halfBoardAvailable + ", fullBoardAvailable=" + fullBoardAvailable 
				+ ", availableRoomsIds=[ ";
		
		for(Integer i: availableRoomsIds) {
			hotelResource = hotelResource + i + " ";
		}
					
		return hotelResource + "]]";
	}
	
	
}
