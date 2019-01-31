package com.oreilly.cloud.predicate;

import java.time.LocalDateTime;

import com.oreilly.cloud.model.QFlight;
import com.oreilly.cloud.object.FlightTime;
import com.oreilly.cloud.object.JourneyStage;
import com.oreilly.cloud.object.SearchFlightRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public final class FlightPredicates {
	
	public static Predicate getSearchFlightsPredicate(SearchFlightRequest searchFlightRequest) {
		BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		LocalDateTime[] departureTimestamps = getDatesTimes(searchFlightRequest.getDepartureTime(), true);
		LocalDateTime[] arrivalTimestamps = getDatesTimes(searchFlightRequest.getArrivalTime(), false);
		
		setCompanyInPredicate(predicate, flight, searchFlightRequest.getCompany());
		setSourceInPredicate(predicate, flight, searchFlightRequest.getSource());
		setDestinationInPredicate(predicate, flight, searchFlightRequest.getDestination());
		setSeatNumberInPredicate(predicate, flight, searchFlightRequest.getSeatNumber(), searchFlightRequest.getSeatType());
		setTimeInPredicate(predicate, flight, departureTimestamps[0], departureTimestamps[1], arrivalTimestamps[1], arrivalTimestamps[0]);
		
		System.out.println("valore query: " + predicate.toString());
		
		return predicate;
	}
	
	public static Predicate getCheckFlightAvailabilityPredicate(int flightId, String seatClass, int seatNumber) {
		BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		predicate.and(flight.flightId.eq(flightId));
		setSeatNumberInPredicate(predicate, flight, seatNumber, seatClass);
		
		return predicate;
	}
	
	
	private static LocalDateTime[] getDatesTimes(FlightTime theFlightTime, boolean isDepartureDate) {
		LocalDateTime[] theDatesTimes = new LocalDateTime[2];
		
		if(theFlightTime != null && !(theFlightTime.getMinute() == 0 && theFlightTime.getHour() == 0 && 
				theFlightTime.getDay() == 0 && theFlightTime.getMonth() == 0 && theFlightTime.getYear() == 0)) {
			if(isDepartureDate) {
				theDatesTimes[0] = buildLocalDateTime(theFlightTime, "departureTime");
				theDatesTimes[1] = buildLocalDateTime(theFlightTime, "departureTime").withHour(23).withMinute(59).withSecond(59);
			} else {
				theDatesTimes[1] = buildLocalDateTime(theFlightTime, "arrivalTime");
				theDatesTimes[0] = buildLocalDateTime(theFlightTime, "arrivalTime").withHour(0).withMinute(0).withSecond(1);
			}
		}
		
		return theDatesTimes;
	}
	
	private static LocalDateTime buildLocalDateTime(FlightTime time, String paramName) {
		if(time == null) {
			return null;
		}
		
		int hour = 0; 
		int minute = 0;
		int second = 0;
		
		if(time.getHour() > 0) {
			hour = time.getHour();
			minute = time.getMinute();
		} else {
			if(paramName.equals("arrivalTime")) {
				hour = 23;
				minute = 59;
				second = 59;
			} else {
				hour = 0;
				minute = 0;
				second = 1;
			}
		}
		
		LocalDateTime dateTime = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDay(), hour, minute, second);
		
		return dateTime;
	}
	
	private static void setCompanyInPredicate(BooleanBuilder predicate, QFlight flight, String company) {
		if(company != null && company != "") {
			predicate.and(flight.company.name.equalsIgnoreCase(company));
		}
	}
	
	private static void setSourceInPredicate(BooleanBuilder predicate, QFlight flight, JourneyStage sourceStage) {
		if(sourceStage != null) {
			if(sourceStage.getAirportName() != null && !sourceStage.getAirportName().equals("")) {
				predicate.and(flight.sourceAirport.name.equalsIgnoreCase(sourceStage.getAirportName()));
			}
			if(sourceStage.getCity() != null && !sourceStage.getCity().equals("")) {
				predicate.and(flight.sourceCity.name.equalsIgnoreCase(sourceStage.getCity()));
			}
			if(sourceStage.getCountry() != null && !sourceStage.getCountry().equals("")) {
				predicate.and(flight.sourceCountry.name.equalsIgnoreCase(sourceStage.getCountry()));
			}
			if(sourceStage.getAirportCode() != null && !sourceStage.getAirportCode().equals("")) {
				predicate.and(flight.sourceAirport.code.equalsIgnoreCase(sourceStage.getAirportCode()));
			}
		}
	}
	
	private static void setDestinationInPredicate(BooleanBuilder predicate, QFlight flight, JourneyStage destinationStage) {
		if(destinationStage != null) {
			if(destinationStage.getAirportName() != null && !destinationStage.getAirportName().equals("")) {
				predicate.and(flight.destinationAirport.name.equalsIgnoreCase(destinationStage.getAirportName()));
			}
			if(destinationStage.getCity() != null && !destinationStage.getCity().equals("")) {
				predicate.and(flight.destinationCity.name.equalsIgnoreCase(destinationStage.getCity()));
			}
			if(destinationStage.getCountry() != null && !destinationStage.getCountry().equals("")) {
				predicate.and(flight.destinationCountry.name.equalsIgnoreCase(destinationStage.getCountry()));
			}
			if(destinationStage.getAirportCode() != null && !destinationStage.getAirportCode().equals("")) {
				predicate.and(flight.destinationAirport.code.equalsIgnoreCase(destinationStage.getAirportCode()));
			}
		}
	}
	
	private static void setSeatNumberInPredicate(BooleanBuilder predicate, QFlight flight, int seatNumber, String seatType) {
		if(seatType != null) {
			if(seatType.equals("economy")) {
				predicate.and(flight.availableEconomySeats.goe(seatNumber));
			} else if(seatType.equals("business")) {
				predicate.and(flight.availableBusinessSeats.goe(seatNumber));	
			} else if(seatType.equals("first")) {
				predicate.and(flight.availableFirstSeats.goe(seatNumber));	
			} 
		} else {
			predicate.and((flight.availableEconomySeats.add(flight.availableBusinessSeats).add(flight.availableFirstSeats)).goe(seatNumber));	
		}
	}
	
	private static void setTimeInPredicate(BooleanBuilder predicate, QFlight flight, LocalDateTime departureTime, 
			LocalDateTime departureLimitTime, LocalDateTime arrivalTime, LocalDateTime arrivalLimitTime) {
		
		if(departureTime != null && departureLimitTime != null) {
			predicate.and(flight.departureTime.between(departureTime, departureLimitTime));
		}
		if(arrivalTime != null && arrivalLimitTime != null) {
			predicate.and(flight.arrivalTime.between(arrivalLimitTime, arrivalTime));
		}
	}
	

}
