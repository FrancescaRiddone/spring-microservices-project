package com.oreilly.cloud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="invalid_token")
public class InvalidToken {
	
	@Id
	@Column(name = "token_id")
    private String tokenId;

	
	public InvalidToken() {
		
	}
	
	public InvalidToken(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Override
	public String toString() {
		return "InvalidToken [tokenId=" + tokenId + "]";
	}

	
}
