package com.oreilly.cloud.object;

public class SearchFlightRequest {
	
	private JourneyStage source;
	
	private JourneyStage destination;
    
    private String seatType;
    
    private int seatNumber;
    

    public SearchFlightRequest() {
    	this.source = new JourneyStage();
		this.destination = new JourneyStage();
		this.seatType = "";
    }

    public SearchFlightRequest(JourneyStage source, JourneyStage destination, String seatType, int seatNumber) {
		this.source = source;
		this.destination = destination;
		this.seatType = seatType;
		this.seatNumber = seatNumber;
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
		return "SearchFlightRequest [source=" + source + ", destination=" + destination + ", seatType=" + seatType
				+ ", seatNumber=" + seatNumber + "]";
	}

    
}
