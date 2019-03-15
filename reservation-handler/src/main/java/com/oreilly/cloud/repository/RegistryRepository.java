package com.oreilly.cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oreilly.cloud.model.ReservationElement;

@Repository
public interface RegistryRepository extends JpaRepository<ReservationElement, Integer> {

	@Query("SELECT e.reservationId FROM ReservationElement e " + 
			"WHERE e.type = :type and " +
			"e.userId = :userId and " +
			"e.confirmed = 1")
	public List<Integer> findElementsInRegistryByUserIdAndType(@Param("userId") int userId, @Param("type") String type);
	
	@Query("SELECT e FROM ReservationElement e " + 
			"WHERE e.reservationId = :reservationId and " +
			"e.userId = :userId and " +
			"e.type = :type and " +
			"e.confirmed = 1")
	public ReservationElement findElementInRegistryByUserIdAndReservationIdAndType(@Param("userId") int userId, @Param("reservationId") int reservationId, @Param("type") String type);

}
