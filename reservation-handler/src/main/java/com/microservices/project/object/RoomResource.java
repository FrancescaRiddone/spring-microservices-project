package com.microservices.project.object;

public class RoomResource {
	
	private int roomId;
	
	private String hotel;
	
	private int hostsNumber;
	
	private double standardDailyPrice;
	
	private double withBreakfastDailyPrice;
	
	private double halfBoardDailyPrice;
	
	private double fullBoardDailyPrice;
	
	private int singleBeds;
	
	private int doubleBeds;
	
	private boolean airConditioner;
	
	private boolean heat;
	
	private boolean tv;
	
	private boolean telephone;
	
	private boolean safe;
	
	private boolean bathtub;
	
	private boolean swimmingPool;
	
	private boolean soundproofing;
	
	private boolean withView;
	
	private boolean bathroom;
	
	private boolean balcony;
	
	
	public RoomResource() {
		
	}

	public RoomResource(int roomId, String hotel, int hostsNumber, double standardDailyPrice,
			double withBreakfastDailyPrice, double halfBoardDailyPrice, double fullBoardDailyPrice, int singleBeds,
			int doubleBeds, boolean airConditioner, boolean heat, boolean tv, boolean telephone, boolean safe,
			boolean bathtub, boolean swimmingPool, boolean soundproofing, boolean withView, boolean bathroom,
			boolean balcony) {
		
		this.roomId = roomId;
		this.hotel = hotel;
		this.hostsNumber = hostsNumber;
		this.standardDailyPrice = standardDailyPrice;
		this.withBreakfastDailyPrice = withBreakfastDailyPrice;
		this.halfBoardDailyPrice = halfBoardDailyPrice;
		this.fullBoardDailyPrice = fullBoardDailyPrice;
		this.singleBeds = singleBeds;
		this.doubleBeds = doubleBeds;
		this.airConditioner = airConditioner;
		this.heat = heat;
		this.tv = tv;
		this.telephone = telephone;
		this.safe = safe;
		this.bathtub = bathtub;
		this.swimmingPool = swimmingPool;
		this.soundproofing = soundproofing;
		this.withView = withView;
		this.bathroom = bathroom;
		this.balcony = balcony;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public int getHostsNumber() {
		return hostsNumber;
	}

	public void setHostsNumber(int hostsNumber) {
		this.hostsNumber = hostsNumber;
	}

	public double getStandardDailyPrice() {
		return standardDailyPrice;
	}

	public void setStandardDailyPrice(double standardDailyPrice) {
		this.standardDailyPrice = standardDailyPrice;
	}

	public double getWithBreakfastDailyPrice() {
		return withBreakfastDailyPrice;
	}

	public void setWithBreakfastDailyPrice(double withBreakfastDailyPrice) {
		this.withBreakfastDailyPrice = withBreakfastDailyPrice;
	}

	public double getHalfBoardDailyPrice() {
		return halfBoardDailyPrice;
	}

	public void setHalfBoardDailyPrice(double halfBoardDailyPrice) {
		this.halfBoardDailyPrice = halfBoardDailyPrice;
	}

	public double getFullBoardDailyPrice() {
		return fullBoardDailyPrice;
	}

	public void setFullBoardDailyPrice(double fullBoardDailyPrice) {
		this.fullBoardDailyPrice = fullBoardDailyPrice;
	}

	public int getSingleBeds() {
		return singleBeds;
	}

	public void setSingleBeds(int singleBeds) {
		this.singleBeds = singleBeds;
	}

	public int getDoubleBeds() {
		return doubleBeds;
	}

	public void setDoubleBeds(int doubleBeds) {
		this.doubleBeds = doubleBeds;
	}

	public boolean isAirConditioner() {
		return airConditioner;
	}

	public void setAirConditioner(boolean airConditioner) {
		this.airConditioner = airConditioner;
	}

	public boolean isHeat() {
		return heat;
	}

	public void setHeat(boolean heat) {
		this.heat = heat;
	}

	public boolean isTv() {
		return tv;
	}

	public void setTv(boolean tv) {
		this.tv = tv;
	}

	public boolean isTelephone() {
		return telephone;
	}

	public void setTelephone(boolean telephone) {
		this.telephone = telephone;
	}

	public boolean isVault() {
		return safe;
	}

	public void setVault(boolean safe) {
		this.safe = safe;
	}

	public boolean isBathtub() {
		return bathtub;
	}

	public void setBathtub(boolean bathtub) {
		this.bathtub = bathtub;
	}

	public boolean isSwimmingPool() {
		return swimmingPool;
	}

	public void setSwimmingPool(boolean swimmingPool) {
		this.swimmingPool = swimmingPool;
	}

	public boolean isSoundproofing() {
		return soundproofing;
	}

	public void setSoundproofing(boolean soundproofing) {
		this.soundproofing = soundproofing;
	}

	public boolean isWithView() {
		return withView;
	}

	public void setWithView(boolean withView) {
		this.withView = withView;
	}

	public boolean isBathroom() {
		return bathroom;
	}

	public void setBathroom(boolean bathroom) {
		this.bathroom = bathroom;
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	@Override
	public String toString() {
		return "RoomResource [roomId=" + roomId + ", hotel=" + hotel + ", hostsNumber=" + hostsNumber
				+ ", standardDailyPrice=" + standardDailyPrice + ", withBreakfastDailyPrice=" + withBreakfastDailyPrice
				+ ", halfBoardDailyPrice=" + halfBoardDailyPrice + ", fullBoardDailyPrice=" + fullBoardDailyPrice
				+ ", singleBeds=" + singleBeds + ", doubleBeds=" + doubleBeds + ", airConditioner=" + airConditioner
				+ ", heat=" + heat + ", tv=" + tv + ", telephone=" + telephone + ", safe=" + safe + ", bathtub="
				+ bathtub + ", swimmingPool=" + swimmingPool + ", soundproofing=" + soundproofing + ", withView="
				+ withView + ", bathroom=" + bathroom + ", balcony=" + balcony + "]";
	}
	
	
}
