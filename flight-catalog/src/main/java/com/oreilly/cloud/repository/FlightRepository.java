package com.oreilly.cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oreilly.cloud.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
	
	
	@Query(	"select f from Flight f where " +
    		"LOWER(f.sourceAirport.name) LIKE LOWER(concat('%', :sourceAirport,'%')) and " +
    		"LOWER(f.sourceCity.name) LIKE LOWER(concat('%', :sourceCity,'%')) and " +
    		"LOWER(f.sourceCountry.name) LIKE LOWER(concat('%', :sourceCountry,'%')) and " +
    		"LOWER(f.destinationAirport.name) LIKE LOWER(concat('%', :destinationAirport,'%')) and " +
    		"LOWER(f.destinationCity.name) LIKE LOWER(concat('%', :destinationCity,'%')) and " +
    		"LOWER(f.destinationCountry.name) LIKE LOWER(concat('%', :destinationCountry,'%')) and " +
    		"(f.availableEconomySeats + f.availableBusinessSeats + f.availableFirstSeats) >= :seatNumber")
	List<Flight> findBySourceDestinationSeatNumberAndAvailability(
			@Param("sourceAirport") String sourceAirport,
			@Param("sourceCity") String sourceCity,
    		@Param("sourceCountry") String sourceCountry,
    		@Param("destinationAirport") String destinationAirport,
    		@Param("destinationCity") String destinationCity,
    		@Param("destinationCountry") String destinationCountry,
    		@Param("seatNumber") Integer seatNumber);
	
	@Query(	"select f from Flight f where " +
    		"LOWER(f.sourceAirport.name) LIKE LOWER(concat('%', :sourceAirport,'%')) and " +
    		"LOWER(f.sourceCity.name) LIKE LOWER(concat('%', :sourceCity,'%')) and " +
    		"LOWER(f.sourceCountry.name) LIKE LOWER(concat('%', :sourceCountry,'%')) and " +
    		"LOWER(f.destinationAirport.name) LIKE LOWER(concat('%', :destinationAirport,'%')) and " +
    		"LOWER(f.destinationCity.name) LIKE LOWER(concat('%', :destinationCity,'%')) and " +
    		"LOWER(f.destinationCountry.name) LIKE LOWER(concat('%', :destinationCountry,'%')) and " +
    		"f.availableEconomySeats >= :seatNumber")
	List<Flight> findBySourceDestinationSeatNumberAndEconomyAvailability(
			@Param("sourceAirport") String sourceAirport,
			@Param("sourceCity") String sourceCity,
    		@Param("sourceCountry") String sourceCountry,
    		@Param("destinationAirport") String destinationAirport,
    		@Param("destinationCity") String destinationCity,
    		@Param("destinationCountry") String destinationCountry,
    		@Param("seatNumber") Integer seatNumber);
	
	@Query(	"select f from Flight f where " +
    		"LOWER(f.sourceAirport.name) LIKE LOWER(concat('%', :sourceAirport,'%')) and " +
    		"LOWER(f.sourceCity.name) LIKE LOWER(concat('%', :sourceCity,'%')) and " +
    		"LOWER(f.sourceCountry.name) LIKE LOWER(concat('%', :sourceCountry,'%')) and " +
    		"LOWER(f.destinationAirport.name) LIKE LOWER(concat('%', :destinationAirport,'%')) and " +
    		"LOWER(f.destinationCity.name) LIKE LOWER(concat('%', :destinationCity,'%')) and " +
    		"LOWER(f.destinationCountry.name) LIKE LOWER(concat('%', :destinationCountry,'%')) and " +
    		"f.availableBusinessSeats >= :seatNumber")
	List<Flight> findBySourceDestinationSeatNumberAndBusinessAvailability(
			@Param("sourceAirport") String sourceAirport,
			@Param("sourceCity") String sourceCity,
    		@Param("sourceCountry") String sourceCountry,
    		@Param("destinationAirport") String destinationAirport,
    		@Param("destinationCity") String destinationCity,
    		@Param("destinationCountry") String destinationCountry,
    		@Param("seatNumber") Integer seatNumber);
	
	@Query(	"select f from Flight f where " +
    		"LOWER(f.sourceAirport.name) LIKE LOWER(concat('%', :sourceAirport,'%')) and " +
    		"LOWER(f.sourceCity.name) LIKE LOWER(concat('%', :sourceCity,'%')) and " +
    		"LOWER(f.sourceCountry.name) LIKE LOWER(concat('%', :sourceCountry,'%')) and " +
    		"LOWER(f.destinationAirport.name) LIKE LOWER(concat('%', :destinationAirport,'%')) and " +
    		"LOWER(f.destinationCity.name) LIKE LOWER(concat('%', :destinationCity,'%')) and " +
    		"LOWER(f.destinationCountry.name) LIKE LOWER(concat('%', :destinationCountry,'%')) and " +
    		"f.availableFirstSeats >= :seatNumber")
	List<Flight> findBySourceDestinationSeatNumberAndFirstAvailability(
			@Param("sourceAirport") String sourceAirport,
			@Param("sourceCity") String sourceCity,
    		@Param("sourceCountry") String sourceCountry,
    		@Param("destinationAirport") String destinationAirport,
    		@Param("destinationCity") String destinationCity,
    		@Param("destinationCountry") String destinationCountry,
    		@Param("seatNumber") Integer seatNumber);
	
	
	 @Query( "select f from Flight f where " + 
	    		"f.flightId = :flightId and " + 
	    		"f.availableEconomySeats >= :seatNumber")
	 Flight findByIdSeatNumberAndEconomyAvailability(@Param("flightId") Integer flightId, @Param("seatNumber") Integer seatNumber);
	 
	 @Query( "select f from Flight f where " + 
	    		"f.flightId = :flightId and " + 
	    		"f.availableBusinessSeats >= :seatNumber")
	 Flight findByIdSeatNumberAndBusinessAvailability(@Param("flightId") Integer flightId, @Param("seatNumber") Integer seatNumber);
	 
	 @Query( "select f from Flight f where " + 
	    		"f.flightId = :flightId and " + 
	    		"f.availableFirstSeats >= :seatNumber")
	 Flight findByIdSeatNumberAndFirstAvailability(@Param("flightId") Integer flightId, @Param("seatNumber") Integer seatNumber);
	 
	 @Modifying
	 @Query("update Flight f set f.availableEconomySeats = f.availableEconomySeats - :seatNumber " +
	    		"where flightId = :flightId")
	 void updateAvailableEconomySeats(@Param("flightId") Integer flightId,
	    							@Param("seatNumber") Integer seatNumber);
	  
	 @Modifying
     @Query("update Flight f set f.availableBusinessSeats = f.availableBusinessSeats - :seatNumber " +
    		 	"where flightId = :flightId")
     void updateAvailableBusinessSeats(@Param("flightId") Integer flightId, 
    								@Param("seatNumber") Integer seatNumber);
    
	 @Modifying
     @Query("update Flight f set f.availableFirstSeats = f.availableFirstSeats - :seatNumber " +
    		 	"where flightId = :flightId")
     void updateAvailableFirstSeats(@Param("flightId") Integer flightId, 
    								@Param("seatNumber") Integer seatNumber);
	 
	
	
}
