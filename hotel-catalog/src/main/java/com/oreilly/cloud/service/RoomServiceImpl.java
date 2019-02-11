package com.oreilly.cloud.service;

import static com.oreilly.cloud.predicate.RoomPredicates.getSearchRoomPredicate;
import static com.oreilly.cloud.service.Converter.convertInRoomResource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.Room;
import com.oreilly.cloud.object.CheckTime;
import com.oreilly.cloud.object.RoomResource;
import com.oreilly.cloud.object.SearchHotelRequest;
import com.oreilly.cloud.repository.RoomRepository;
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
		
		Predicate thePredicate = getSearchRoomPredicate(searchHotelRequest, hotelIds);
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
		
		RoomResource roomResource = convertInRoomResource(theRoom);
		
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
