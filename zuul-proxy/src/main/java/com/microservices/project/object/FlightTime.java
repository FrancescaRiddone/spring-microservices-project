package com.microservices.project.object;

public class FlightTime {
	
	private int minute;
	
	private int hour;
    
    private int day;
    
    private int month;
    
    private int year;
    
    
    public FlightTime() {
		
	}

	public FlightTime(int minute, int hour, int day, int month, int year) {
		this.minute = minute;
		this.hour = hour;
		this.day = day;
		this.month = month;
		this.year = year;
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
		return "FlightTime [minute=" + minute + ", hour=" + hour + ", day=" + day + ", month=" + month + ", year="
				+ year + "]";
	}
	

}
