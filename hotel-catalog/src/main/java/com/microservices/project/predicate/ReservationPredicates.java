package com.microservices.project.predicate;

import java.time.LocalDateTime;
import java.util.List;

import com.microservices.project.model.QReservation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public final class ReservationPredicates {
	
	public static Predicate getReservedRoomsPredicate(LocalDateTime startDate, LocalDateTime endDate, List<Integer> roomIds) {
		BooleanBuilder predicate = new BooleanBuilder();
		QReservation reservation = QReservation.reservation;

		predicate.and(reservation.room.id.in(roomIds));
		predicate.and(reservation.confirmed.eq(true));
		predicate.and( ( reservation.checkIn.loe(startDate).and(reservation.checkOut.goe(startDate)) ).or( reservation.checkIn.between(startDate, endDate) ) );
		
		System.out.println("valore query: " + predicate.toString());
		
		return predicate;
	}
	

}
