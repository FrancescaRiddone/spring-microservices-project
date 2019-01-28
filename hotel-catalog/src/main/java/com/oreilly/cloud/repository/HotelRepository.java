package com.oreilly.cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oreilly.cloud.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

	//DA FINIRE
	
	@Query(	"select h from Hotel h where " +
    		"LOWER(h.) LIKE LOWER(concat('%', :sourceAirport,'%')) and " +
    		"LOWER(f.sourceCity.name) LIKE LOWER(concat('%', :sourceCity,'%')) and " +
    		"LOWER(f.sourceCountry.name) LIKE LOWER(concat('%', :sourceCountry,'%')) and " +
    		"LOWER(f.destinationAirport.name) LIKE LOWER(concat('%', :destinationAirport,'%')) and " +
    		"LOWER(f.destinationCity.name) LIKE LOWER(concat('%', :destinationCity,'%')) and " +
    		"LOWER(f.destinationCountry.name) LIKE LOWER(concat('%', :destinationCountry,'%')) and " +
    		"(f.availableEconomySeats + f.availableBusinessSeats + f.availableFirstSeats) >= :seatNumber")
	List<Hotel> find(
			@Param("city") String city,
			@Param("hostsNumber") Integer hostsNumber,
			@Param("stars") Integer stars,
			@Param("wifi") Integer wifi,
			@Param("parking") Integer parking,
			@Param("restaurant") Integer restaurant,
			@Param("forDisabledPeople") Integer forDisabledPeople,
			@Param("gym") Integer gym,
			@Param("spa") Integer spa,
			@Param("swimmingPool") Integer swimmingPool,
			@Param("reservationType") Integer reservationType
			 
			
			
			);
	
	/*
	 private double minPrice;
	
	private double maxPrice;
	
	private int singleBeds;
	
	private int doubleBeds;
	
	private boolean airConditioner;
	
	private boolean heat;
	
	private boolean tv;
	
	private boolean telephone;
	
	private boolean vault;
	
	private boolean bathtub;
	
	private boolean privateSwimmingPool;
	
	private boolean soundproofing;
	
	private boolean withView;
	
	private boolean bathroom;
	
	private boolean balcony;
	 */
	
	
}
