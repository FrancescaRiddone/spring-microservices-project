package com.oreilly.cloud.service;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

public interface HotelService {
	
	public JSONObject getHotels(String city, int checkInDay, int checkInMonth, int checkInYear, int checkOutDay, int checkOutMonth,
			 int checkOutYear, int hostsNumber, double minPrice, double maxPrice, String reservationType, int stars, boolean wifi,
			 boolean parking, boolean restaurant, boolean forDisabledPeople, boolean gym, boolean spa,
			@RequestParam("swimmingPool") boolean swimmingPool,
			@RequestParam("singleBeds") int singleBeds, 
			@RequestParam("doubleBed") boolean doubleBed, 
			@RequestParam("airConditioner") boolean airConditioner, 
			@RequestParam("heat") boolean heat, 
			@RequestParam("tv") boolean tv, 
			@RequestParam("telephone") boolean telephone, 
			@RequestParam("kettle") boolean kettle, 
			@RequestParam("coffeeMachine") boolean coffeeMachine, 
			@RequestParam("bathtub") boolean bathtub, 
			@RequestParam("privateSwimmingPool") boolean privateSwimmingPool,
			@RequestParam("soundproofing") boolean soundproofing,
			@RequestParam("withView") boolean withView,
			@RequestParam("bathroom") boolean bathroom,
			@RequestParam("balcony") boolean balcony)

}
