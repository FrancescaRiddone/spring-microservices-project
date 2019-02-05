package com.oreilly.cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.oreilly.cloud.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>, QuerydslPredicateExecutor<Room>{
	
	@Query(	"SELECT r " + 
			"FROM Room r " +
			"WHERE r.hotel.id = :hotelId")
	public List<Room> findByHotelId(@Param("hotelId") int hotelId);

	
}
