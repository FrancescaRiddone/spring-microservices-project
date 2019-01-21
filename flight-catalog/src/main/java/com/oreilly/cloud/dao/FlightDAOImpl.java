package com.oreilly.cloud.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.object.SearchFlightRequest;

@Repository
public class FlightDAOImpl implements FlightDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public List<Flight> getFlights(SearchFlightRequest searchFlightRequest) {
		
		if(searchFlightRequest.getSeatNumber() == 0) {
			searchFlightRequest.setSeatNumber(1);
		}
		
		Session currentSession = sessionFactory.getCurrentSession();
		String queryText = setQueryString(searchFlightRequest);
		Query<Flight> theQuery = currentSession.createQuery(queryText, Flight.class);
		theQuery = setQueryParameters(theQuery, searchFlightRequest);
		List<Flight> theFlights = theQuery.getResultList();
		if(theFlights != null) {
			theFlights = filterFlightsByTime(theFlights, searchFlightRequest);
		}
		
		return theFlights;
	}
	
	@Override
	public Flight getFlight(int flightId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Flight theFlight = currentSession.get(Flight.class, flightId);
		
		return theFlight;
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
	
	
	private String setQueryString(SearchFlightRequest searchFlightRequest) {
		String queryString = "select f from Flight f where ";
		queryString = evaluateStringInsertion(queryString, "sourceAirport", searchFlightRequest.getSource().getAirportName());
		queryString = evaluateStringInsertion(queryString, "sourceCity", searchFlightRequest.getSource().getCity());
		queryString = evaluateStringInsertion(queryString, "sourceCountry", searchFlightRequest.getSource().getCountry());
		queryString = evaluateStringInsertion(queryString, "destinationAirport", searchFlightRequest.getDestination().getAirportName());
		queryString = evaluateStringInsertion(queryString, "destinationCity", searchFlightRequest.getDestination().getCity());
		queryString = evaluateStringInsertion(queryString, "destinationCountry", searchFlightRequest.getDestination().getCountry());
		queryString = evaluateStringInsertion(queryString, "seatType", searchFlightRequest.getSeatType());
		queryString = queryString.concat("(f.availableEconomySeats + f.availableBusinessSeats + f.availableFirstSeats) >= :seatNumber");
		
		return queryString;
	}
	
	private String evaluateStringInsertion(String query, String requestParamName, String requestParamValue) {
		if(requestParamValue != null && !requestParamValue.equals("")) {
			query = query.concat("f." + requestParamName + ".name = :" + requestParamName + " and ");
		} 
		
		return query;
	}
	
	private Query<Flight> setQueryParameters(Query<Flight> theQuery, SearchFlightRequest searchFlightRequest){
		
		theQuery = setQueryParameter(theQuery, "sourceAirport", searchFlightRequest.getSource().getAirportName());
		theQuery = setQueryParameter(theQuery, "sourceCity", searchFlightRequest.getSource().getCity());
		theQuery = setQueryParameter(theQuery, "sourceCountry", searchFlightRequest.getSource().getCountry());
		theQuery = setQueryParameter(theQuery, "destinationAirport", searchFlightRequest.getDestination().getAirportName());
		theQuery = setQueryParameter(theQuery, "destinationCity", searchFlightRequest.getDestination().getCity());
		theQuery = setQueryParameter(theQuery, "destinationCountry", searchFlightRequest.getDestination().getCountry());
		theQuery = setQueryParameter(theQuery, "seatType", searchFlightRequest.getSeatType());
		theQuery = setQueryParameter(theQuery, "seatNumber", new Integer(searchFlightRequest.getSeatNumber()));
		
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
	
	private List<Flight> filterFlightsByTime(List<Flight> theFlights, SearchFlightRequest searchFlightRequest){
		
		List<Flight> filteredFlights = new ArrayList<>();
	    
	    for(Flight theFlight: theFlights) {
	    	if(checkTravelTime(theFlight.getDepartureTime(), searchFlightRequest.getSource().getHour(), searchFlightRequest.getSource().getDay(), 
	    			searchFlightRequest.getSource().getMonth(), searchFlightRequest.getSource().getYear()) &&
	    		checkTravelTime(theFlight.getArrivalTime(), searchFlightRequest.getDestination().getHour(), searchFlightRequest.getDestination().getDay(), 
	    				searchFlightRequest.getDestination().getMonth(), searchFlightRequest.getDestination().getYear())) {
	    		
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
