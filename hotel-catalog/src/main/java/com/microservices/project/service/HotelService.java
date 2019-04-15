package com.microservices.project.service;

import java.util.List;

import com.microservices.project.object.HotelResource;
import com.microservices.project.object.SearchHotelRequest;

public interface HotelService {
	
	public HotelResource getHotel(int hotelId);
	
	public List<HotelResource> getHotels(SearchHotelRequest searchHotelRequest);

}
