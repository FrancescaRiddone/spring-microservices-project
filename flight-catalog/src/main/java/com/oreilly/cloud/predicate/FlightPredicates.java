package com.oreilly.cloud.predicate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.cloud.model.QFlight;
import com.oreilly.cloud.object.FlightTime;
import com.oreilly.cloud.object.JourneyStage;
import com.oreilly.cloud.object.SearchFlightRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public final class FlightPredicates {
	
	private FlightPredicates() {
		
	}
	
	public static Predicate getSearchFlightsPredicate(SearchFlightRequest searchFlightRequest) {
		BooleanBuilder predicate = new BooleanBuilder();
		QFlight flight = QFlight.flight;
		
		Timestamp[] departureTimestamps = getDatesTimestamp(searchFlightRequest.getDepartureTime(), true);
		Timestamp[] arrivalTimestamps = getDatesTimestamp(searchFlightRequest.getArrivalTime(), false);
		
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
	
	
	private static Timestamp[] getDatesTimestamp(FlightTime theFlightTime, boolean isDepartureDate) {
		Timestamp[] theDatesTimestamps = new Timestamp[2];
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		if(theFlightTime != null && !(theFlightTime.getMinute() == 0 && theFlightTime.getHour() == 0 && 
				theFlightTime.getDay() == 0 && theFlightTime.getMonth() == 0 && theFlightTime.getYear() == 0)) {
			if(isDepartureDate) {
				String dateString = buildDateString(theFlightTime, "departureTime");
				if(dateString != null) {
					try {
						Date departureDate = formatter.parse(dateString);
						theDatesTimestamps[0] = new Timestamp(departureDate.getTime());
						Date departureLimit = formatter.parse(dateString.substring(0, 11).concat("23:59:59"));
						theDatesTimestamps[1] = new Timestamp(departureLimit.getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			} else {
				String dateString = buildDateString(theFlightTime, "arrivalTime");
				if(dateString != null) {
					try {
						Date arrivalDate = formatter.parse(dateString);
						theDatesTimestamps[1] = new Timestamp(arrivalDate.getTime());
						Date arrivalLimit = formatter.parse(dateString.substring(0, 11).concat("00:00:01"));
						theDatesTimestamps[0] = new Timestamp(arrivalLimit.getTime());
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return theDatesTimestamps;
	}
	
	private static String buildDateString(FlightTime time, String paramName) {
		if(time == null) {
			return null;
		}
		
		String dateString = time.getYear() + "-";
		
		if(time.getMonth() < 10) {
			dateString = dateString.concat("0");
		}
		dateString = dateString.concat(time.getMonth() + "-");
		
		if(time.getDay() < 10) {
			dateString = dateString.concat("0");
		}
		dateString = dateString.concat(time.getDay() + "");
		
		if(time.getHour() > 0 || time.getMinute() > 0) {
			dateString = dateString.concat(" ");
			
			if(time.getHour() < 10) {
				dateString = dateString.concat("0");
			}
			dateString = dateString.concat(time.getHour() + ":");
			
			if(time.getMinute() < 10) {
				dateString = dateString.concat("0");
			}
			dateString = dateString.concat(time.getMinute() + ":00");	
		} else {
			if(paramName.equals("arrivalTime")) {
				dateString = dateString.concat(" 23:59:59");
			} else {
				dateString = dateString.concat(" 00:00:01");
			}
		}
		
		return dateString;
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
	
	private static void setTimeInPredicate(BooleanBuilder predicate, QFlight flight, Timestamp departureTimestamp, 
		Timestamp departureLimitTimestamp, Timestamp arrivalTimestamp, Timestamp arrivalLimitTimestamp) {
		
		if(departureTimestamp != null && departureLimitTimestamp != null) {
			predicate.and(flight.departureTime.between(departureTimestamp, departureLimitTimestamp));
		}
		if(arrivalTimestamp != null && arrivalLimitTimestamp != null) {
			predicate.and(flight.arrivalTime.between(arrivalLimitTimestamp, arrivalTimestamp));
		}
	}
	

}
