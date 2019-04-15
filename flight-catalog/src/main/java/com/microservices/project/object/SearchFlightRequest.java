package com.microservices.project.object;

public class SearchFlightRequest {
	
	private String company;
	
	private JourneyStage source;
	
	private JourneyStage destination;
	
	private FlightTime departureTime;
	
	private FlightTime arrivalTime;
    
    private String seatType;
    
    private int seatNumber;
    

    public SearchFlightRequest() {
    	
    }

    public SearchFlightRequest(String company, JourneyStage source, JourneyStage destination, FlightTime departureTime,
			FlightTime arrivalTime, String seatType, int seatNumber) {
		
		this.company = company;
		this.source = source;
		this.destination = destination;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.seatType = seatType;
		this.seatNumber = seatNumber;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public JourneyStage getSource() {
		return source;
	}

	public void setSource(JourneyStage source) {
		this.source = source;
	}

	public JourneyStage getDestination() {
		return destination;
	}

	public void setDestination(JourneyStage destination) {
		this.destination = destination;
	}
	
	public FlightTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(FlightTime departureTime) {
		this.departureTime = departureTime;
	}

	public FlightTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(FlightTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	@Override
	public String toString() {
		return "SearchFlightRequest [source=" + source + ", destination=" + destination + ", departureTime="
				+ departureTime + ", arrivalTime=" + arrivalTime + ", seatType=" + seatType + ", seatNumber="
				+ seatNumber + "]";
	}

    
}
