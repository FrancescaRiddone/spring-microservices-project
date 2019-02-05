package com.oreilly.cloud.service;

import java.util.List;

import com.oreilly.cloud.model.Room;
import com.oreilly.cloud.object.RoomResource;
import com.oreilly.cloud.object.SearchHotelRequest;


public interface RoomService {
	
	public List<Room> getRooms(SearchHotelRequest searchHotelRequest, List<Integer> hotelsIds);
	
	public List<Room> getRooms(int hotelId);
	
	public Room getRoom(int roomId);
	
	public RoomResource getRoomResource(int roomId);

}
