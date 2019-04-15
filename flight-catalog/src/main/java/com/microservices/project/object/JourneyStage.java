package com.microservices.project.object;

public class JourneyStage {
	
	private String airportName;
	
	private String airportCode;
	
	private String city;
	
	private String country;

	
	public JourneyStage() {
		
	}
	
	public JourneyStage(String airportName, String airportCode, String city, String country) {
		this.airportName = airportName;
		this.airportCode = airportCode;
		this.city = city;
		this.country = country;
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

	@Override
	public String toString() {
		return "JourneyStage [airportName=" + airportName + ", airportCode=" + airportCode + ", city=" + city
				+ ", country=" + country + "]";
	}

	
}
