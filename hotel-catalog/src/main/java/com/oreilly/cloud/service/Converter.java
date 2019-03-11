package com.oreilly.cloud.service;

import java.time.LocalDateTime;

import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.object.HotelResource;
import com.oreilly.cloud.object.RoomResource;
import com.oreilly.cloud.model.Hotel;
import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.model.Room;
import com.oreilly.cloud.object.CheckTime;


public class Converter{

	public static HotelResource convertInHotelResource(Hotel theHotel) {
		HotelResource theHotelResource = new HotelResource(theHotel.getId(),
														theHotel.getName(),
														theHotel.getCity().getName(),
														theHotel.getCountry().getName(),
														theHotel.getAddress(),
														theHotel.getStars(),
														theHotel.isWifi(),
														theHotel.isParking(),
														theHotel.isRestaurant(),
														theHotel.isForDisabledPeople(),
														theHotel.isGym(),
														theHotel.isSpa(),
														theHotel.isSwimmingPool(),
														theHotel.isBreakfastAvailability(),
														theHotel.isHalfBoardAvailability(),
														theHotel.isFullBoardAvailability());
				
		return theHotelResource;
	}
	
	public static RoomResource convertInRoomResource(Room theRoom) {
		RoomResource theRoomResource = new RoomResource(theRoom.getId(),
														theRoom.getHotel().getName(),
														theRoom.getHostsNumber(),
														theRoom.getStandardDailyPrice(),
														theRoom.getWithBreakfastDailyPrice(),
														theRoom.getHalfBoardDailyPrice(),
														theRoom.getFullBoardDailyPrice(),
														theRoom.getSingleBeds(),
														theRoom.getDoubleBeds(),
														theRoom.isAirConditioner(),
														theRoom.isHeat(),
														theRoom.isTv(),
														theRoom.isTelephone(),
														theRoom.isVault(),
														theRoom.isBathtub(),
														theRoom.isSwimmingPool(),
														theRoom.isSoundproofing(),
														theRoom.isWithView(),
														theRoom.isBathroom(),
														theRoom.isBalcony());
		
		return theRoomResource;
	}
	
	public static HotelReservationResource convertInReservationResource(Reservation theReservation) {
		RoomResource theRoomResource = convertInRoomResource(theReservation.getRoom());
		LocalDateTime checkInTime = theReservation.getCheckIn();
		CheckTime checkIn = new CheckTime(checkInTime.getDayOfMonth(), checkInTime.getMonthValue(), checkInTime.getYear());
		LocalDateTime checkOutTime = theReservation.getCheckOut();
		CheckTime checkOut = new CheckTime(checkOutTime.getDayOfMonth(), checkOutTime.getMonthValue(), checkOutTime.getYear());
		
		HotelReservationResource theReservationResource = new HotelReservationResource(	theReservation.getId(),
																						theReservation.getUserEmail(),
																						theRoomResource,
																						theReservation.getPrice(),
																						theReservation.getReservationType(),
																						theReservation.getHostsNumber(),
																						checkIn,
																						checkOut);
		
		return theReservationResource;
	}

}
