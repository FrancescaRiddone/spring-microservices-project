package com.microservices.project.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class ApplicationUser {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
    private int userId;
	
	@Column(name="username")
    private String username;
    
	@Column(name="password")
    private String password;
	
	@JoinColumn(name = "user_details_id", unique = true)
	@OneToOne(cascade = CascadeType.ALL)
	private UserDetails details;
    

	public ApplicationUser() {
	
	}

	public ApplicationUser(int userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	public ApplicationUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDetails getDetails() {
		return details;
	}

	public void setDetails(UserDetails details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId+ ", username=" + username + ", password=" + password + ", details=" + details + "]";
	}
	

}
