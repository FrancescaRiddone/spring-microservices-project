package com.microservices.project.object;

public class HotelProperties {
	 
	 private int stars;
	 
	 private boolean wifi;
	 
	 private boolean parking;
	 
	 private boolean restaurant;
	 
	 private boolean forDisabledPeople;
	 
	 private boolean gym;
	 
	 private boolean spa;
	 
	 private boolean swimmingPool;
	 
	 private String reservationType;
	 
	 
	 public HotelProperties() {
		 
	 }

	 public HotelProperties(int stars, boolean wifi, boolean parking, boolean restaurant, boolean forDisabledPeople,
			boolean gym, boolean spa, boolean swimmingPool, String reservationType) {
		
		this.stars = stars;
		this.wifi = wifi;
		this.parking = parking;
		this.restaurant = restaurant;
		this.forDisabledPeople = forDisabledPeople;
		this.gym = gym;
		this.spa = spa;
		this.swimmingPool = swimmingPool;
		this.reservationType = reservationType;
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

	public String getReservationType() {
		return reservationType;
	}

	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}

	@Override
	public String toString() {
		return "HotelProperties [stars=" + stars + ", wifi=" + wifi + ", parking=" + parking + ", restaurant="
				+ restaurant + ", forDisabledPeople=" + forDisabledPeople + ", gym=" + gym + ", spa=" + spa
				+ ", swimmingPool=" + swimmingPool + ", reservationType=" + reservationType + "]";
	}
	  

}
