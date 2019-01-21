package com.oreilly.cloud.object;

public class JourneyStage {
	
	private String airportName;
	
	private String airportCode;
	
	private String city;
	
	private String country;
	
	private int minute;
	
	private int hour;
    
    private int day;
    
    private int month;
    
    private int year;

	
	public JourneyStage() {
		this.airportName = "";
		this.airportCode = "";
		this.city = "";
		this.country = "";
	}
	
	public JourneyStage(String airportName, String airportCode, String city, String country, int minute, int hour,
			int day, int month, int year) {
		
		this.airportName = airportName;
		this.airportCode = airportCode;
		this.city = city;
		this.country = country;
		this.minute = minute;
		this.hour = hour;
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "JourneyStage [airportName=" + airportName + ", airportCode=" + airportCode + ", city=" + city
				+ ", country=" + country + ", minute=" + minute + ", hour=" + hour + ", day=" + day + ", month=" + month
				+ ", year=" + year + "]";
	}
	
	
}
