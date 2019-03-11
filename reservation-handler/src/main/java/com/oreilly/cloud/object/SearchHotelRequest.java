package com.oreilly.cloud.object;

public class SearchHotelRequest {
	
	private String city;
	
	private CheckTime checkIn;
	
	private CheckTime checkOut;
	
	private int hostsNumber;
	
	private HotelProperties hotelProperties;
	
	private RoomProperties roomProperties;
	
	
	public SearchHotelRequest() {
		
	}

	public SearchHotelRequest(String city, CheckTime checkIn, CheckTime checkOut, int hostsNumber,
			HotelProperties hotelProperties, RoomProperties roomProperties) {
		
		this.city = city;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.hostsNumber = hostsNumber;
		this.hotelProperties = hotelProperties;
		this.roomProperties = roomProperties;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public CheckTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(CheckTime checkIn) {
		this.checkIn = checkIn;
	}

	public CheckTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(CheckTime checkOut) {
		this.checkOut = checkOut;
	}

	public int getHostsNumber() {
		return hostsNumber;
	}

	public void setHostsNumber(int hostsNumber) {
		this.hostsNumber = hostsNumber;
	}

	public HotelProperties getHotelProperties() {
		return hotelProperties;
	}

	public void setHotelProperties(HotelProperties hotelProperties) {
		this.hotelProperties = hotelProperties;
	}

	public RoomProperties getRoomProperties() {
		return roomProperties;
	}

	public void setRoomProperties(RoomProperties roomProperties) {
		this.roomProperties = roomProperties;
	}

	@Override
	public String toString() {
		return "SearchHotelRequest [city=" + city + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", hostsNumber="
				+ hostsNumber + ", hotelProperties=" + hotelProperties + ", roomProperties=" + roomProperties + "]";
	}


}
