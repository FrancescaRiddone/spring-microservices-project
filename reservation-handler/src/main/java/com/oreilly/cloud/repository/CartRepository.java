package com.oreilly.cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oreilly.cloud.model.CartElement;

@Repository
public interface CartRepository extends JpaRepository<CartElement, Integer> {
	
	@Query("SELECT e.reservationId FROM CartElement e " + 
			"WHERE e.type = :type and " +
			"e.userId = :userId")
	public List<Integer> findElementsInCartByUserIdAndType(@Param("userId") int userId, @Param("type") String type);
	
	@Modifying
	@Query("DELETE FROM CartElement e " + 
			"WHERE e.type = :type and " +
			"e.reservationId = :reservationId")
	public void deleteByReservationIdAndType(@Param("reservationId") int reservationId, @Param("type") String type);

}
