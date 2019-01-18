package com.oreilly.cloud.service;

public class SearchFlightRequest {
    private String sourceAirport;
    private String sourceCity;
    private String sourceCountry;
    private String destinationAirport;
    private String destinationCity;
    private String destinationCountry;
    private int departureHour;
    private int departureDay;
    private int departureMonth;
    private int departureYear;
    private int arrivalHour;
    private int arrivalDay;
    private int arrivalMonth;
    private int arrivalYear;
    private String seatType;
    private int seatNumber;

    public SearchFlightRequest(String sourceAirport, String sourceCity, String sourceCountry, String destinationAirport, String destinationCity, String destinationCountry, int departureHour, int departureDay, int departureMonth, int departureYear, int arrivalHour, int arrivalDay, int arrivalMonth, int arrivalYear, String seatType, int seatNumber) {
        this.sourceAirport = sourceAirport;
        this.sourceCity = sourceCity;
        this.sourceCountry = sourceCountry;
        this.destinationAirport = destinationAirport;
        this.destinationCity = destinationCity;
        this.destinationCountry = destinationCountry;
        this.departureHour = departureHour;
        this.departureDay = departureDay;
        this.departureMonth = departureMonth;
        this.departureYear = departureYear;
        this.arrivalHour = arrivalHour;
        this.arrivalDay = arrivalDay;
        this.arrivalMonth = arrivalMonth;
        this.arrivalYear = arrivalYear;
        this.seatType = seatType;
        this.seatNumber = seatNumber;
    }

    public SearchFlightRequest() {

    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public void setSourceAirport(String sourceAirport) {
        this.sourceAirport = sourceAirport;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getSourceCountry() {
        return sourceCountry;
    }

    public void setSourceCountry(String sourceCountry) {
        this.sourceCountry = sourceCountry;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public int getDepartureHour() {
        return departureHour;
    }

    public void setDepartureHour(int departureHour) {
        this.departureHour = departureHour;
    }

    public int getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(int departureDay) {
        this.departureDay = departureDay;
    }

    public int getDepartureMonth() {
        return departureMonth;
    }

    public void setDepartureMonth(int departureMonth) {
        this.departureMonth = departureMonth;
    }

    public int getDepartureYear() {
        return departureYear;
    }

    public void setDepartureYear(int departureYear) {
        this.departureYear = departureYear;
    }

    public int getArrivalHour() {
        return arrivalHour;
    }

    public void setArrivalHour(int arrivalHour) {
        this.arrivalHour = arrivalHour;
    }

    public int getArrivalDay() {
        return arrivalDay;
    }

    public void setArrivalDay(int arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    public int getArrivalMonth() {
        return arrivalMonth;
    }

    public void setArrivalMonth(int arrivalMonth) {
        this.arrivalMonth = arrivalMonth;
    }

    public int getArrivalYear() {
        return arrivalYear;
    }

    public void setArrivalYear(int arrivalYear) {
        this.arrivalYear = arrivalYear;
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
}
