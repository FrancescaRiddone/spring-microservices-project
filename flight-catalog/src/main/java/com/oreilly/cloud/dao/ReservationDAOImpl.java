package com.oreilly.cloud.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oreilly.cloud.entity.Flight;
import com.oreilly.cloud.entity.Reservation;

@Repository
public class ReservationDAOImpl implements ReservationDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public Reservation getReservation(int reservationId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Reservation theReservation = currentSession.get(Reservation.class, reservationId);
		
		return theReservation;
	}
	
	@Override
	public Reservation saveReservation(Reservation theReservation) {
		Session currentSession = sessionFactory.getCurrentSession();
		theReservation.setPrice(getPriceForClassAndSeatNumber(theReservation.getFlight(), theReservation.getSeatsType(), 
				theReservation.getSeatsNumber()));
		currentSession.saveOrUpdate(theReservation);
		
		return theReservation;
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
