package com.oreilly.cloud.service;

import java.util.List;

import com.oreilly.cloud.object.HotelResource;
import com.oreilly.cloud.object.SearchHotelRequest;

public interface HotelService {
	
	public HotelResource getHotel(int hotelId);
	
	public List<HotelResource> getHotels(SearchHotelRequest searchHotelRequest);

}
