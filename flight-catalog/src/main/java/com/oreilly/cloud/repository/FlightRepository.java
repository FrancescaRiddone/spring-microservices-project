package com.oreilly.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oreilly.cloud.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer>, QuerydslPredicateExecutor<Flight> {
	
	@Modifying
	@Query("update Flight f set f.availableEconomySeats = f.availableEconomySeats - :seatNumber " +
			"where flightId = :flightId")
	public void updateAvailableEconomySeats(@Param("flightId") Integer flightId,
			@Param("seatNumber") Integer seatNumber);
	  
	@Modifying
	@Query("update Flight f set f.availableBusinessSeats = f.availableBusinessSeats - :seatNumber " +
			"where flightId = :flightId")
	public void updateAvailableBusinessSeats(@Param("flightId") Integer flightId, 
			@Param("seatNumber") Integer seatNumber);
	
	@Modifying
	@Query("update Flight f set f.availableFirstSeats = f.availableFirstSeats - :seatNumber " +
			"where flightId = :flightId")
	public void updateAvailableFirstSeats(@Param("flightId") Integer flightId, 
			@Param("seatNumber") Integer seatNumber);
	 
	
}
