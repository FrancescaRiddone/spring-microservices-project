package com.microservices.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microservices.project.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	
	@Modifying
	@Query("update Reservation r set r.confirmed = 1" +
			"where reservation_id = :reservationId")
	public void updateReservationConfirmation(@Param("reservationId") Integer reservationId);

}
