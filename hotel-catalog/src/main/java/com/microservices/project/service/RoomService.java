package com.microservices.project.service;

import java.util.List;

import com.microservices.project.model.Room;
import com.microservices.project.object.RoomResource;
import com.microservices.project.object.SearchHotelRequest;


public interface RoomService {
	
	public List<Room> getRooms(SearchHotelRequest searchHotelRequest, List<Integer> hotelsIds);
	
	public List<Room> getRooms(int hotelId);
	
	public Room getRoom(int roomId);
	
	public RoomResource getRoomResource(int roomId);

}
