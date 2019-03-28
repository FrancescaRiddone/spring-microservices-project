package com.oreilly.cloud.object;

public class BankDetails {
	
	String cardType;
	
	String accountNumber;
	
	String cardOwner;
	
	int expiryMonth;
	
	int expiryYear;
	
	int securityCode;

	
	public BankDetails() {
		
	}

	public BankDetails(String cardType, String accountNumber, String cardOwner, int expiryMonth, int expiryYear, int securityCode) {
		
		this.cardType = cardType;
		this.accountNumber = accountNumber;
		this.cardOwner = cardOwner;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.securityCode = securityCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public int getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}

	public int getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}

	@Override
	public String toString() {
		return "BankDetails [cardType=" + cardType + ", accountNumber=" + accountNumber + ", cardOwner=" + cardOwner
				+ ", expiryMonth=" + expiryMonth + ", expiryYear=" + expiryYear + ", securityCode=" + securityCode
				+ "]";
	}
	

}
