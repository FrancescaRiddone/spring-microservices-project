package com.oreilly.cloud.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.entity.Reservation;

@Repository
public class ReservationDAOImpl implements ReservationDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public JSONObject getReservation(int reservationId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Reservation theReservation = currentSession.get(Reservation.class, reservationId);
		JSONObject theReservationJSON = com.oreilly.cloud.dao.UtilsForObjectsConversion.createJSONForReservation(theReservation);
		
		return theReservationJSON;
	}
	
	@Override
	public JSONObject saveReservation(Flight flight, String userName, String userSurname, String seatClass, int seatNumber) {
		Session currentSession = sessionFactory.getCurrentSession();
		Reservation theNewReservation= new Reservation(flight, userName, userSurname, getPriceForClassAndSeatNumber(flight, seatClass, seatNumber), 
											seatClass, seatNumber, true);
		currentSession.saveOrUpdate(theNewReservation);
		JSONObject theSavedReservationJSON = com.oreilly.cloud.dao.UtilsForObjectsConversion.createJSONForReservation(theNewReservation);
		
		return theSavedReservationJSON;
	}
	
	private double getPriceForClassAndSeatNumber(Flight flight, String seatClass, int seatNumber) {
		if(seatClass.equals("economy")) {
			return (flight.getEconomySeatPrice() * seatNumber);
		} else if(seatClass.equals("business")) {
			return (flight.getBusinessSeatPrice() * seatNumber);
		}
		return (flight.getFirstSeatPrice() * seatNumber);
	}

	
}
