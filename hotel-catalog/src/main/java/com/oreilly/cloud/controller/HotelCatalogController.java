package com.oreilly.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.object.HotelResource;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.object.HotelReservationRequest;
import com.oreilly.cloud.object.SearchHotelRequest;
import com.oreilly.cloud.service.HotelService;
import com.oreilly.cloud.service.ReservationService;

@RestController
@RequestMapping("/hotels")
public class HotelCatalogController {
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private ReservationService reservationService;
	
	
	@GetMapping("/hotel")
	public HotelResource getHotel(@RequestParam("hotelId") int hotelId) throws ValidateException, ResourceNotFoundException {
		HotelResource resource = hotelService.getHotel(hotelId);
		
		return resource;
	}
	/*
	@PostMapping("/requiredHotels")
	public List<HotelResource> getHotels(@RequestBody SearchHotelRequest searchHotelRequest) {
		return hotelService.getFlights(searchHotelRequest);
	}
	*/
	
	
	/*
	 * Dò all'utente info dettagliate sulle camere offerte da un certo hotel 
	 * (tutte anche quelle che magari non sono ciò che cerca)
	 */
	/*
	@GetMapping("/")
	public void getHotelRooms(@RequestParam("hotelId") int hotelId) {
		
	}
	*/
	/*
	@GetMapping("/reservations/reservation")
	public HotelReservationResource getReservation(@RequestParam("reservationId") int reservationId) {
		return null;
	}
	*/
	/*
	@GetMapping("/reservations")
	public List<HotelReservationResource> getReservations(@RequestParam List<Integer> reservationIds) {
		return null;
	}
	*/
	
	/*
	 * Crea una nuova reservation con i seguenti valori
	 */
	/*
	@PostMapping("/")
	public void createReservation(@RequestBody HotelReservationRequest newReservationRequest) {
		
	}
	*/
	/*
	 * Conferma una reservation con id corrispondente
	 */
	/*
	@PostMapping("/")
	public void confirmReservation(@RequestParam int reservationId) {
		
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
