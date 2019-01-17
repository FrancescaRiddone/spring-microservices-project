package com.oreilly.cloud.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oreilly.cloud.entity.Flight;

@Repository
public class FlightDAOImpl implements FlightDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public List<JSONObject> getFlights(String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport,
			String destinationCity, String destinationCountry, int departureHour, int departureDay, int departureMonth, int departureYear,
			int arrivalHour, int arrivalDay, int arrivalMonth, int arrivalYear, String seatType, int seatNumber) {
		
		if(seatNumber == 0) {
			seatNumber = 1;
		}
		
		Session currentSession = sessionFactory.getCurrentSession();
		String queryText = setQueryString(sourceAirport, sourceCity, sourceCountry, destinationAirport, destinationCity,
				destinationCountry, seatType, seatNumber);
		Query<Flight> theQuery = currentSession.createQuery(queryText, Flight.class);
		theQuery = setQueryParameters(theQuery, sourceAirport, sourceCity, sourceCountry, destinationAirport, destinationCity,
				destinationCountry, seatType, seatNumber);
		List<Flight> theFlights = theQuery.getResultList();
		if(theFlights != null) {
			theFlights = filterFlightsByTime(theFlights, departureHour, departureDay, departureMonth, departureYear, 
					arrivalHour, arrivalDay, arrivalMonth, arrivalYear);
		}
		List<JSONObject> theFlightsJSON = new ArrayList<>();
		
		for(Flight theFlight: theFlights) {
			JSONObject theFlightJSON = com.oreilly.cloud.dao.UtilsForObjectsConversion.createJSONForFlight(theFlight);
			theFlightsJSON.add(theFlightJSON);
		}
		
		return theFlightsJSON;
	}
	
	@Override
	public JSONObject getFlight(int flightId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Flight theFlight = currentSession.get(Flight.class, flightId);
		JSONObject theFlightJSON = com.oreilly.cloud.dao.UtilsForObjectsConversion.createJSONForFlight(theFlight);
		
		return theFlightJSON;
	}
	
	@Override
	public Flight checkFlightAvailability(int flightId, String seatClass, int seatNumber) {
		Session currentSession = sessionFactory.getCurrentSession();
		String queryText = getQueryWithClassEvaluation("select f from Flight f where f.flightId = :flightId", seatClass, seatNumber);
		if(queryText == null) {
			return null;
		}
		Query<Flight> theQuery = currentSession.createQuery(queryText, Flight.class);
		theQuery.setParameter("flightId", flightId);
		theQuery.setParameter("seatNumber", seatNumber);
		
		return theQuery.getSingleResult();
	}
	
	
	private String setQueryString(String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport,
			String destinationCity, String destinationCountry, String seatType, int seatNumber) {
		
		String queryString = "select f from Flight f where ";
		queryString = evaluateStringInsertion(queryString, "sourceAirport", sourceAirport);
		queryString = evaluateStringInsertion(queryString, "sourceCity", sourceCity);
		queryString = evaluateStringInsertion(queryString, "sourceCountry", sourceCountry);
		queryString = evaluateStringInsertion(queryString, "destinationAirport", destinationAirport);
		queryString = evaluateStringInsertion(queryString, "destinationCity", destinationCity);
		queryString = evaluateStringInsertion(queryString, "destinationCountry", destinationCountry);
		queryString = evaluateStringInsertion(queryString, "seatType", seatType);
		queryString = queryString.concat("(f.availableEconomySeats + f.availableBusinessSeats + f.availableFirstSeats) >= :seatNumber");
		
		return queryString;
	}
	
	private String evaluateStringInsertion(String query, String requestParamName, String requestParamValue) {
		if(requestParamValue != null && !requestParamValue.equals("")) {
			query = query.concat("f." + requestParamName + ".name = :" + requestParamName + " and ");
		} 
		
		return query;
	}
	
	private Query<Flight> setQueryParameters(Query<Flight> theQuery, String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport,
			String destinationCity, String destinationCountry, String seatType, int seatNumber){
		
		theQuery = setQueryParameter(theQuery, "sourceAirport", sourceAirport);
		theQuery = setQueryParameter(theQuery, "sourceCity", sourceCity);
		theQuery = setQueryParameter(theQuery, "sourceCountry", sourceCountry);
		theQuery = setQueryParameter(theQuery, "destinationAirport", destinationAirport);
		theQuery = setQueryParameter(theQuery, "destinationCity", destinationCity);
		theQuery = setQueryParameter(theQuery, "destinationCountry", destinationCountry);
		theQuery = setQueryParameter(theQuery, "seatType", seatType);
		theQuery = setQueryParameter(theQuery, "seatNumber", new Integer(seatNumber));
		
		return  theQuery;
	}
	
	private Query<Flight> setQueryParameter(Query<Flight> theQuery, String requestParamName, Object requestParamValue){
		if(requestParamValue != null && !requestParamValue.equals("")) {
			theQuery.setParameter(requestParamName, requestParamValue);
		}
		
		return theQuery;
	}	
	
	private String getQueryWithClassEvaluation(String queryText, String seatClass, int seatNumber) {
		switch(seatClass) {
		case "economy":
			queryText = queryText.concat(" and f.availableEconomySeats >= :seatNumber");
			break;
		
		case "business":
			queryText = queryText.concat(" and f.availableBusinessSeats >= :seatNumber");
			break;
		
		case "first":
			queryText = queryText.concat(" and f.availableFirstSeats >= :seatNumber");
			break;
		
		default:
			return null;
		}
		
		return queryText;
	}
	
	private List<Flight> filterFlightsByTime(List<Flight> theFlights, int departureHour, int departureDay, 
			int departureMonth, int departureYear, int arrivalHour, int arrivalDay, int arrivalMonth, int arrivalYear){
		
		List<Flight> filteredFlights = new ArrayList<>();
	    
	    for(Flight theFlight: theFlights) {
	    	if(checkTravelTime(theFlight.getDepartureTime(), departureHour, departureDay, departureMonth, departureYear) &&
	    		checkTravelTime(theFlight.getArrivalTime(), arrivalHour, arrivalDay, arrivalMonth, arrivalYear)) {
	    		
	    		filteredFlights.add(theFlight);
	    	}
		}
	    return filteredFlights;
	}
	
	private boolean checkTravelTime(Timestamp flightTimestamp, int hour, int day, int month, int year){
		if(flightTimestamp == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(flightTimestamp);
		if(year != 0) {
			if(cal.get(Calendar.YEAR) != year) {
				return false;
			}
			
			if(month != 0) {
				if(cal.get(Calendar.MONTH) + 1 != month) {
					return false;
				}

				if(day != 0) {
					if(cal.get(Calendar.DAY_OF_MONTH) != day) {
						return false;
					}
					
					if(hour != 0) {
						if(cal.get(Calendar.HOUR_OF_DAY) != hour) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}


}
