package com.oreilly.cloud.predicate;

import java.util.List;

import com.oreilly.cloud.model.QRoom;
import com.oreilly.cloud.object.RoomProperties;
import com.oreilly.cloud.object.SearchHotelRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public final class RoomPredicates {
	
	public static Predicate getSearchRoomPredicate(SearchHotelRequest searchHotelRequest, List<Integer> hotelIds) {
		BooleanBuilder predicate = new BooleanBuilder();
		QRoom room = QRoom.room;

		predicate.and(room.hotel.id.in(hotelIds));
		predicate.and(room.hostsNumber.eq(searchHotelRequest.getHostsNumber()));
		setRoomPropertiesInPredicate(predicate, room, searchHotelRequest);
		
		System.out.println("valore query: " + predicate.toString());
		
		return predicate;
	}
	
	
	private static void setRoomPropertiesInPredicate(BooleanBuilder predicate, QRoom room, SearchHotelRequest searchHotelRequest) {
		RoomProperties roomProperties = searchHotelRequest.getRoomProperties();
		
		setPricesInPredicate(predicate, room, searchHotelRequest);
		setBedsNumberInPredicate(predicate, room, roomProperties.getSingleBeds(), roomProperties.getDoubleBeds());
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isAirConditioner(), "airConditioner");
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isHeat(), "heat");
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isTv(), "tv");
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isTelephone(), "telephone");
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isVault(), "vault");
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isBathtub(), "bathtub");
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isPrivateSwimmingPool(), "privateSwimmingPool");
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isSoundproofing(), "soundproofing");
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isWithView(), "withView");
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isBathroom(), "bathroom");
		setBooleanPropertyInPredicate(predicate, room, roomProperties.isBalcony(), "balcony");
		
	}
	
	private static void setPricesInPredicate(BooleanBuilder predicate, QRoom room, SearchHotelRequest searchHotelRequest) {
		setMinPriceInPredicate(predicate, room, searchHotelRequest.getRoomProperties().getMinPrice(), searchHotelRequest.getHotelProperties().getReservationType());
		setMaxPriceInPredicate(predicate, room, searchHotelRequest.getRoomProperties().getMaxPrice(), searchHotelRequest.getHotelProperties().getReservationType());
	}
	
	private static void setMinPriceInPredicate(BooleanBuilder predicate, QRoom room, double minPrice, String reservationType) {
		if(minPrice > 0.0) {
			if(reservationType == null) {
				predicate.and(room.standardDailyPrice.goe(minPrice));
			} else if(reservationType.equals("breakfast")) {
				predicate.and(room.withBreakfastDailyPrice.goe(minPrice));
			} else if(reservationType.equals("half board")) {
				predicate.and(room.halfBoardDailyPrice.goe(minPrice));
			} else if(reservationType.equals("full board")) {
				predicate.and(room.fullBoardDailyPrice.goe(minPrice));
			} else {
				predicate.and(room.standardDailyPrice.goe(minPrice));
			}	
		}
	}
	
	private static void setMaxPriceInPredicate(BooleanBuilder predicate, QRoom room, double maxPrice, String reservationType) {
		if(maxPrice > 0.0) {
			if(reservationType == null) {
				predicate.and(room.standardDailyPrice.loe(maxPrice));
			} else if(reservationType.equals("breakfast")) {
				predicate.and(room.withBreakfastDailyPrice.loe(maxPrice));
			} else if(reservationType.equals("half board")) {
				predicate.and(room.halfBoardDailyPrice.loe(maxPrice));
			} else if(reservationType.equals("full board")) {
				predicate.and(room.fullBoardDailyPrice.loe(maxPrice));
			} else {
				predicate.and(room.standardDailyPrice.loe(maxPrice));
			}	
		}
	}
	
	private static void setBedsNumberInPredicate(BooleanBuilder predicate, QRoom room, int singleBeds, int doubleBeds) {
		if(singleBeds > 0) {
			predicate.and(room.singleBeds.eq(singleBeds));
		}
		if(doubleBeds > 0) {
			predicate.and(room.doubleBeds.eq(doubleBeds));
		}
	}
	
	private static void setBooleanPropertyInPredicate(BooleanBuilder predicate, QRoom room, boolean property, String propertyName) {
		if(property) {
			if(propertyName.equals("airConditioner")) {
				predicate.and(room.airConditioner.eq(true));
			} else if(propertyName.equals("heat")) {
				predicate.and(room.heat.eq(true));
			} else if(propertyName.equals("tv")) {
				predicate.and(room.tv.eq(true));
			} else if(propertyName.equals("telephone")) {
				predicate.and(room.telephone.eq(true));
			} else if(propertyName.equals("vault")) {
				predicate.and(room.vault.eq(true));
			} else if(propertyName.equals("bathtub")) {
				predicate.and(room.bathtub.eq(true));
			} else if(propertyName.equals("privateSwimmingPool")) {
				predicate.and(room.swimmingPool.eq(true));
			} else if(propertyName.equals("soundproofing")) {
				predicate.and(room.soundproofing.eq(true));
			} else if(propertyName.equals("withView")) {
				predicate.and(room.withView.eq(true));
			} else if(propertyName.equals("bathroom")) {
				predicate.and(room.bathroom.eq(true));
			} else if(propertyName.equals("balcony")) {
				predicate.and(room.balcony.eq(true));
			} 
		}
	}
	
	

}
