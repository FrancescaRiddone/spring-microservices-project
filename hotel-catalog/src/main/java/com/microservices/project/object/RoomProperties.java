package com.microservices.project.object;

public class RoomProperties {
	
	private double minPrice;
	
	private double maxPrice;
	
	private int singleBeds;
	
	private int doubleBeds;
	
	private boolean airConditioner;
	
	private boolean heat;
	
	private boolean tv;
	
	private boolean telephone;
	
	private boolean safe;
	
	private boolean bathtub;
	
	private boolean privateSwimmingPool;
	
	private boolean soundproofing;
	
	private boolean withView;
	
	private boolean bathroom;
	
	private boolean balcony;
	
	
	public RoomProperties() {
		
	}

	public RoomProperties(double minPrice, double maxPrice, int singleBeds, int doubleBeds, boolean airConditioner,
			boolean heat, boolean tv, boolean telephone, boolean safe, boolean bathtub, boolean privateSwimmingPool,
			boolean soundproofing, boolean withView, boolean bathroom, boolean balcony) {
		
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.singleBeds = singleBeds;
		this.doubleBeds = doubleBeds;
		this.airConditioner = airConditioner;
		this.heat = heat;
		this.tv = tv;
		this.telephone = telephone;
		this.safe = safe;
		this.bathtub = bathtub;
		this.privateSwimmingPool = privateSwimmingPool;
		this.soundproofing = soundproofing;
		this.withView = withView;
		this.bathroom = bathroom;
		this.balcony = balcony;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
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

	public boolean isSafe() {
		return safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	public boolean isBathtub() {
		return bathtub;
	}

	public void setBathtub(boolean bathtub) {
		this.bathtub = bathtub;
	}

	public boolean isPrivateSwimmingPool() {
		return privateSwimmingPool;
	}

	public void setPrivateSwimmingPool(boolean privateSwimmingPool) {
		this.privateSwimmingPool = privateSwimmingPool;
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
		return "RoomProperties [minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", singleBeds=" + singleBeds
				+ ", doubleBeds=" + doubleBeds + ", airConditioner=" + airConditioner + ", heat=" + heat + ", tv=" + tv
				+ ", telephone=" + telephone + ", safe=" + safe + ", bathtub=" + bathtub + ", privateSwimmingPool="
				+ privateSwimmingPool + ", soundproofing=" + soundproofing + ", withView=" + withView + ", bathroom="
				+ bathroom + ", balcony=" + balcony + "]";
	}


}
