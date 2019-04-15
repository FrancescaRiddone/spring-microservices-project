package com.microservices.project.predicate;

import com.microservices.project.model.QHotel;
import com.microservices.project.object.HotelProperties;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public final class HotelPredicates {
	
	public static Predicate getSearchHotelPredicate(HotelProperties hotelProperties, String city) {
		BooleanBuilder predicate = new BooleanBuilder();
		QHotel hotel = QHotel.hotel;
		
		predicate.and(hotel.city.name.equalsIgnoreCase(city));
		setHotelPropertiesInPredicate(predicate, hotel, hotelProperties);
		
		System.out.println("valore query: " + predicate.toString());
		
		return predicate;
	}
	
	private static void setHotelPropertiesInPredicate(BooleanBuilder predicate, QHotel hotel, HotelProperties hotelProperties) {
		setStarsInPredicate(predicate, hotel, hotelProperties.getStars());
		setBooleanPropertyInPredicate(predicate, hotel, hotelProperties.isWifi(), "wifi");
		setBooleanPropertyInPredicate(predicate, hotel, hotelProperties.isParking(), "parking");
		setBooleanPropertyInPredicate(predicate, hotel, hotelProperties.isRestaurant(), "restaurant");
		setBooleanPropertyInPredicate(predicate, hotel, hotelProperties.isForDisabledPeople(), "forDisabledPeople");
		setBooleanPropertyInPredicate(predicate, hotel, hotelProperties.isGym(), "gym");
		setBooleanPropertyInPredicate(predicate, hotel, hotelProperties.isSpa(), "spa");
		setBooleanPropertyInPredicate(predicate, hotel, hotelProperties.isSwimmingPool(), "swimmingPool");
		setReservationTypeInPredicate(predicate, hotel, hotelProperties.getReservationType());
	}
	
	private static void setStarsInPredicate(BooleanBuilder predicate, QHotel hotel, int stars) {
		if(stars != 0) {
			predicate.and(hotel.stars.eq(stars));
		}
	}
	
	private static void setBooleanPropertyInPredicate(BooleanBuilder predicate, QHotel hotel, boolean property, String propertyName) {
		if(property) {
			if(propertyName.equals("wifi")) {
				predicate.and(hotel.wifi.eq(true));
			} else if(propertyName.equals("parking")) {
				predicate.and(hotel.parking.eq(true));
			} else if(propertyName.equals("restaurant")) {
				predicate.and(hotel.restaurant.eq(true));
			} else if(propertyName.equals("forDisabledPeople")) {
				predicate.and(hotel.forDisabledPeople.eq(true));
			} else if(propertyName.equals("gym")) {
				predicate.and(hotel.gym.eq(true));
			} else if(propertyName.equals("spa")) {
				predicate.and(hotel.spa.eq(true));
			} else {
				predicate.and(hotel.swimmingPool.eq(true));
			} 
		}
	}
	
	private static void setReservationTypeInPredicate(BooleanBuilder predicate, QHotel hotel, String reservationType) {
		if(reservationType != null && !reservationType.equals("")) {
			if(reservationType.equals("breakfast")) {
				predicate.and(hotel.breakfastAvailability.eq(true));
			} else if(reservationType.equals("half board")) {
				predicate.and(hotel.halfBoardAvailability.eq(true));
			} else if(reservationType.equals("full board")) {
				predicate.and(hotel.fullBoardAvailability.eq(true));
			}
		}
	}
	
	
}
