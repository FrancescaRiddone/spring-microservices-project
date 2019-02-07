package com.oreilly.cloud.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.model.Room;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>, QuerydslPredicateExecutor<Reservation> {
	
	@Query("SELECT r.room FROM Reservation r " + 
			"WHERE r.confirmed = true and " +
			"r.room.id IN (:roomIds) and " +
			"( (r.checkIn <= :startDate and r.checkOut > :startDate) or (r.checkIn >= :startDate and r.checkIn < :endDate) )")
	public List<Room> findReservedRooms(@Param("startDate") LocalDateTime startDate, 
												@Param("endDate") LocalDateTime endDate, 
												@Param("roomIds") List<Integer> roomIds);
	
	
}
