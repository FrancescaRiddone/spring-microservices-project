package com.oreilly.cloud.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oreilly.cloud.service.HotelService;
import com.oreilly.cloud.service.ReservationService;

@RestController
@RequestMapping("/hotels")
public class HotelCatalogController {
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping("/reserve")
	public String reserve() {
		return "reserved......";
	}
	
	@GetMapping("/requiredHotels")
	public JSONObject getHotels(@RequestParam("city") String city, 
									@RequestParam("checkInDay") int checkInDay,
									@RequestParam("checkInMonth") int checkInMonth,
									@RequestParam("checkInYear") int checkInYear,
									@RequestParam("checkOutDay") int checkOutDay,
									@RequestParam("checkOutMonth") int checkOutMonth,
									@RequestParam("checkOutYear") int checkOutYear,
									@RequestParam("hostsNumber") int hostsNumber,
									@RequestParam("minPrice") double minPrice,
									@RequestParam("maxPrice") double maxPrice,
									@RequestParam("reservationType") String reservationType, 
									@RequestParam("stars") int stars,
									@RequestParam("wifi") boolean wifi,
									@RequestParam("parking") boolean parking,
									@RequestParam("restaurant") boolean restaurant,
									@RequestParam("forDisabledPeople") boolean forDisabledPeople,
									@RequestParam("gym") boolean gym,
									@RequestParam("spa") boolean spa,
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
									@RequestParam("balcony") boolean balcony) {
		
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
