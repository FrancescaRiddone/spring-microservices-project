package com.oreilly.cloud.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.HotelReservationRequest;
import com.oreilly.cloud.object.HotelReservationResource;
import com.oreilly.cloud.object.HotelResource;
import com.oreilly.cloud.object.RoomResource;
import com.oreilly.cloud.object.SearchHotelRequest;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ResourceUnavailableException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.model.Room;
import com.oreilly.cloud.service.HotelService;
import com.oreilly.cloud.service.ReservationService;
import com.oreilly.cloud.service.RoomService;

@RestController
@RequestMapping("/hotels")
public class HotelCatalogController {
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ReservationService reservationService;
	
	
	@GetMapping("/hotel/{hotelId}")
	public HotelResource getHotel(@PathVariable int hotelId) throws ValidateException, ResourceNotFoundException {
		HotelResource resource = hotelService.getHotel(hotelId);
		
		List<Room> theHotelRooms = roomService.getRooms(hotelId);
		
		if(theHotelRooms != null && !theHotelRooms.isEmpty()) {
			List<Integer> theRoomIds = new ArrayList<>();
			for(Room theRoom: theHotelRooms) {
				theRoomIds.add(theRoom.getId());
			}
			resource.setAvailableRoomsIds(theRoomIds);
		}
		
		return resource;
	}
	
	@PostMapping("/requiredHotels")
	public List<HotelResource> getHotels(@RequestBody SearchHotelRequest searchHotelRequest) throws ValidateException, ResourceNotFoundException {
		List<HotelResource> foundHotels = hotelService.getHotels(searchHotelRequest);
		List<Integer> foundIds = new ArrayList<>();
		for(HotelResource theHotel: foundHotels) {
			foundIds.add(theHotel.getHotelId());
		}
		List<Room> foundRooms = roomService.getRooms(searchHotelRequest, foundIds);
		foundIds = new ArrayList<>();
		for(Room theRoom: foundRooms) {
			foundIds.add(theRoom.getId());
		}
		List<Room> reservedRooms = reservationService.getReservedRooms(searchHotelRequest.getCheckIn(), searchHotelRequest.getCheckOut(), foundIds);
		List<Room> availableRooms = getAvailableRooms(foundRooms, reservedRooms);
		List<HotelResource> resultHotels = getHotelsWithAvailableRooms(foundHotels, availableRooms);
		 
		return resultHotels;
	}
	
	@GetMapping("/rooms/room/{roomId}")
	public RoomResource getRoom(@PathVariable int roomId) throws ValidateException, ResourceNotFoundException {
		RoomResource theRoom = roomService.getRoomResource(roomId);
		
		return theRoom;
	}
	
	@GetMapping("/rooms/{roomIds}")
	public List<RoomResource> getRooms(@PathVariable List<Integer> roomIds) throws ValidateException, ResourceNotFoundException {
		if(roomIds.isEmpty()) {
			throw new ValidateException();
		}
		
		List<RoomResource> theRooms = new ArrayList<>();
		for(int roomId: roomIds) {
			RoomResource theRoom = roomService.getRoomResource(roomId);
			if(theRoom != null) {
				theRooms.add(theRoom);
			}
		}
		if(theRooms.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		return theRooms;
	}
	
	@GetMapping("/reservations/reservation/{reservationId}")
	public HotelReservationResource getReservation(@PathVariable int reservationId) throws ValidateException, ResourceNotFoundException {
		HotelReservationResource theReservation = reservationService.getReservationResource(reservationId);
		
		return theReservation;
	}
	
	@GetMapping("/reservations/{reservationIds}")
	public List<HotelReservationResource> getReservations(@PathVariable List<Integer> reservationIds) throws ValidateException, ResourceNotFoundException {
		if(reservationIds.isEmpty()) {
			throw new ValidateException();
		}
		
		List<HotelReservationResource> theReservations = new ArrayList<>();
		for(int reservationId: reservationIds) {
			HotelReservationResource theReservation = reservationService.getReservationResource(reservationId);
			if(theReservation != null) {
				theReservations.add(theReservation);
			}
		}
		if(theReservations.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		return theReservations;
	}
	
	@PostMapping("/reservations/new")
	public HotelReservationResource createReservation(@RequestBody HotelReservationRequest hotelReservationRequest) 
			throws ResourceNotFoundException, ValidateException, ResourceUnavailableException {
		
		checkCreateReservationParam(hotelReservationRequest);
		
		Room roomToReserve = roomService.getRoom(hotelReservationRequest.getRoomId());
		if(roomToReserve.getHostsNumber() != hotelReservationRequest.getHostsNumber()) {
			throw new ValidateException();
		}
		
		reservationService.checkRoomAvailability(hotelReservationRequest.getRoomId(), hotelReservationRequest.getHostsNumber(), 
												hotelReservationRequest.getCheckIn(), hotelReservationRequest.getCheckOut());
		
		Room availableRoom = roomService.getRoom(hotelReservationRequest.getRoomId());	
		Reservation theNewReservation = new Reservation(availableRoom, convertInLocalDateTime(hotelReservationRequest.getCheckIn()), 
											convertInLocalDateTime(hotelReservationRequest.getCheckOut()), hotelReservationRequest.getUserEmail(), 
											0.0, hotelReservationRequest.getHostsNumber(), 
											hotelReservationRequest.getReservationType(), false);
		
		return reservationService.saveReservation(theNewReservation);
	}
	
	@PutMapping("/reservations/confirmedReservation/{reservationId}")
	public String confirmReservation(@PathVariable int reservationId) 
			throws ResourceNotFoundException, ValidateException, ResourceUnavailableException {
		
		Reservation theReservation = reservationService.getReservation(reservationId);
		
		reservationService.checkRoomAvailability(theReservation.getRoom().getId(), theReservation.getHostsNumber(), 
				convertInCheckTime(theReservation.getCheckIn()), convertInCheckTime(theReservation.getCheckOut()));
		
		theReservation.setConfirmed(true);
		reservationService.saveReservation(theReservation);
		
		return "Hotel reservation successfully confirmed.";
	}
	
	@DeleteMapping("/reservations/reservation/{reservationId}")
	public String deleteReservation(@PathVariable int reservationId) throws ValidateException, ResourceNotFoundException {
		reservationService.deleteReservation(reservationId);
		
		return "Reservation with id " + reservationId + " deleted with success";
	}
	

	private List<Room> getAvailableRooms(List<Room> foundRooms, List<Room> reservedRooms) {
		List<Room> availableRooms = new ArrayList<>();
		for(Room foundRoom: foundRooms) {
			boolean isReserved = false;
			for(Room reservedRoom: reservedRooms) {
				if(foundRoom.getId() == reservedRoom.getId()) {
					isReserved = true;
				}
			}
			if(!isReserved) {
				availableRooms.add(foundRoom);
			}
		}
		if(availableRooms.isEmpty()) {
			throw new ResourceUnavailableException();
		}
		
		return availableRooms;
	}
	
	private List<HotelResource> getHotelsWithAvailableRooms(List<HotelResource> hotels, List<Room> availableRooms) {
		List<HotelResource> resultHotels = new ArrayList<>();
		for(HotelResource foundHotel: hotels) {
			List<Integer> roomList = new ArrayList<>();
			for(Room availableRoom: availableRooms) {
				if(availableRoom.getHotel().getId() == foundHotel.getHotelId()) {
					roomList.add(availableRoom.getId());
				}
			}
			if(!roomList.isEmpty()) {
				foundHotel.setAvailableRoomsIds(roomList);
				resultHotels.add(foundHotel);
			}
		}
		
		return resultHotels;
	}
	
	private void checkCreateReservationParam(HotelReservationRequest hotelReservationRequest) {
		if(hotelReservationRequest == null) {
			throw new ValidateException();
		}
		if(hotelReservationRequest.getCheckIn() == null || hotelReservationRequest.getCheckOut() == null) {
			throw new ValidateException();
		}
		checkTimes(hotelReservationRequest.getCheckIn(), hotelReservationRequest.getCheckOut());
	}
	
	private void checkTimes(CheckTime startTime, CheckTime endTime) {
		if(startTime.getDay() < 1 || startTime.getDay() > 31 || startTime.getMonth() < 1 || 
				startTime.getMonth() > 12 || startTime.getYear() < 2019 ||
				endTime.getDay() < 1 || endTime.getDay() > 31 || endTime.getMonth() < 1 || 
				endTime.getMonth() > 12 || endTime.getYear() < 2019) {
			
			throw new ValidateException();
		}
		
		LocalDateTime checkIn = convertInLocalDateTime(startTime);
		LocalDateTime checkOut = convertInLocalDateTime(endTime);
		
		if(!checkIn.isBefore(checkOut)) {
			throw new ValidateException();
		}
	}

	private LocalDateTime convertInLocalDateTime(CheckTime theTime) {
		LocalDateTime dateTime = LocalDateTime.of(theTime.getYear(), theTime.getMonth(), theTime.getDay(), 0, 0);
		
		return dateTime;
	}
	
	private CheckTime convertInCheckTime(LocalDateTime theTime) {
		CheckTime checkTime = new CheckTime(theTime.getDayOfMonth(), theTime.getMonthValue(), theTime.getYear());
		
		return checkTime;
	}
	

}
