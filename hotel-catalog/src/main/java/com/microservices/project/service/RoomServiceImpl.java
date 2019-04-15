package com.microservices.project.service;

import java.util.List;
import java.util.Optional;

import com.microservices.project.exception.ResourceNotFoundException;
import com.microservices.project.exception.ValidateException;
import com.microservices.project.predicate.RoomPredicates;
import com.microservices.project.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.microservices.project.model.Room;
import com.microservices.project.object.CheckTime;
import com.microservices.project.object.RoomResource;
import com.microservices.project.object.SearchHotelRequest;
import com.querydsl.core.types.Predicate;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
    RoomRepository roomRepository;

	@Override
	@Transactional
	public List<Room> getRooms(SearchHotelRequest searchHotelRequest, List<Integer> hotelIds) throws ValidateException, ResourceNotFoundException {
		checkGetRoomsParams(searchHotelRequest, hotelIds);
		
		List<Room> foundRooms;
		
		Predicate thePredicate = RoomPredicates.getSearchRoomPredicate(searchHotelRequest, hotelIds);
		Iterable<Room> roomIterator = roomRepository.findAll(thePredicate);
		foundRooms = Lists.newArrayList(roomIterator);
		
		if(foundRooms.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		return foundRooms;
	}
	
	@Override
	@Transactional
	public List<Room> getRooms(int hotelId) {
		if(hotelId <= 0) {
			return null;
		}
		
		List<Room> foundRooms = roomRepository.findByHotelId(hotelId);
		
		return foundRooms;
	}
	
	@Override
	@Transactional
	public Room getRoom(int roomId) throws ValidateException, ResourceNotFoundException {
		if(roomId <= 0) {
			throw new ValidateException();
		}
		
		Optional<Room> theRoom = roomRepository.findById(roomId);
		if(!theRoom.isPresent()) {
			throw new ResourceNotFoundException();
		}
		
		return theRoom.get();
	}

	@Override
	public RoomResource getRoomResource(int roomId) throws ValidateException, ResourceNotFoundException {
		Room theRoom = getRoom(roomId);
		
		RoomResource roomResource = Converter.convertInRoomResource(theRoom);
		
		return roomResource;
	}
	
	
	private void checkGetRoomsParams(SearchHotelRequest searchHotelRequest, List<Integer> hotelIds) {
		if(searchHotelRequest == null) {
			throw new ValidateException();
		}
		checkCity(searchHotelRequest.getCity());
		checkCheckTime(searchHotelRequest.getCheckIn());
		checkCheckTime(searchHotelRequest.getCheckOut());
		if(searchHotelRequest.getHostsNumber() <= 0) {
			searchHotelRequest.setHostsNumber(1);
		}
		if(hotelIds == null) {
			throw new ValidateException();
		} else {
			if(hotelIds.isEmpty()) {
				throw new ValidateException();
			}
		}
	}
	
	private void checkCity(String city) {
		if(city == null) {
			throw new ValidateException();
		} else if(city.equals("")) {
			throw new ValidateException();
		}
	}
	
	private void checkCheckTime(CheckTime checkTime) {
		if(checkTime == null) {
			throw new ValidateException();
		} else if(checkTime.getDay() < 1 || checkTime.getDay() > 31 ||
				checkTime.getMonth() < 1 || checkTime.getMonth() > 12 ||
				checkTime.getYear() < 2019) {
			
			throw new ValidateException();
		}
	}


}
