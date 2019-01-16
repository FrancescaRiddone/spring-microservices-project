package com.oreilly.cloud.dao;

import java.util.ArrayList;
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
			String destinationCity, String destinationCountry, String seatType, int seatNumber) {
		
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
		boolean firstInsertion = true;
		
		String result1 = evaluateStringInsertion(queryString, "sourceAirport", sourceAirport, firstInsertion);
		String result2 = evaluateStringInsertion(result1, "sourceCity", sourceCity, firstInsertion);
		String result3 = evaluateStringInsertion(result2, "sourceCountry", sourceCountry, firstInsertion);
		String result4 = evaluateStringInsertion(result3, "destinationAirport", destinationAirport, firstInsertion);
		String result5 = evaluateStringInsertion(result4, "destinationCity", destinationCity, firstInsertion);
		String result6 = evaluateStringInsertion(result5, "destinationCountry", destinationCountry, firstInsertion);
		String result7 = evaluateStringInsertion(result6, "seatType", seatType, firstInsertion);
		String finalQuery = result7.concat(" and (f.availableEconomySeats + f.availableBusinessSeats + f.availableFirstSeats) >= :seatNumber");
		
		return finalQuery;
	}
	
	private String evaluateStringInsertion(String query, String requestParamName, String requestParamValue, boolean firstInsertion) {
		if(requestParamValue != null && !requestParamValue.equals("")) {
			if(firstInsertion == false) {
				query = query.concat(" and f." + requestParamName + ".name = :" + requestParamName);
			} else {
				firstInsertion = false;
				query = query.concat("f." + requestParamName + ".name = :" + requestParamName);
			}
		}
		
		return query;
	}

	private Query<Flight> setQueryParameters(Query<Flight> theQuery, String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport,
			String destinationCity, String destinationCountry, String seatType, int seatNumber){
		
		Query<Flight> theQuery1 = setQueryParameter(theQuery, "sourceAirport", sourceAirport);
		Query<Flight> theQuery2 = setQueryParameter(theQuery1, "sourceCity", sourceCity);
		Query<Flight> theQuery3 = setQueryParameter(theQuery2, "sourceCountry", sourceCountry);
		Query<Flight> theQuery4 = setQueryParameter(theQuery3, "destinationAirport", destinationAirport);
		Query<Flight> theQuery5 = setQueryParameter(theQuery4, "destinationCity", destinationCity);
		Query<Flight> theQuery6 = setQueryParameter(theQuery5, "destinationCountry", destinationCountry);
		Query<Flight> theQuery7 = setQueryParameter(theQuery6, "seatType", seatType);
		Query<Flight> finalQuery = setQueryParameter(theQuery7, "seatNumber", new Integer(seatNumber));
		
		return  finalQuery;
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


}
