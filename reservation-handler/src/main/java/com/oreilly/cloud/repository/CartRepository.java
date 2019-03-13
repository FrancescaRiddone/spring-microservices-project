package com.oreilly.cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oreilly.cloud.model.ReservationElement;

@Repository
public interface CartRepository extends JpaRepository<ReservationElement, Integer> {
	
	@Query("SELECT e FROM ReservationElement e " + 
			"WHERE e.reservationId = :reservationId and " +
			"e.userId = :userId and " +
			"e.type = :type and " +
			"e.confirmed = 0")
	public ReservationElement findElementInCartByUserIdAndReservationIdAndTpe(@Param("userId") int userId, @Param("reservationId") int reservationId, @Param("type") String type);
	
	@Query("SELECT e.reservationId FROM ReservationElement e " + 
			"WHERE e.type = :type and " +
			"e.userId = :userId and " +
			"e.confirmed = 0")
	public List<Integer> findElementsInCartByUserIdAndType(@Param("userId") int userId, @Param("type") String type);
	
	@Modifying
	@Query("DELETE FROM ReservationElement e " + 
			"WHERE e.type = :type and " +
			"e.reservationId = :reservationId and " + 
			"e.confirmed = 0")
	public void deleteByReservationIdAndType(@Param("reservationId") int reservationId, @Param("type") String type);
	
	@Modifying
	@Query("update ReservationElement e set e.confirmed = 1 " +
			"where userId = :userId and " + 
			"reservationId = :reservationId and " + 
			"type = :type and " + 
			"confirmed = 0")
	public void updateElementConfirmation(@Param("userId") int userId, @Param("reservationId") int reservationId, @Param("type") String type);

}
