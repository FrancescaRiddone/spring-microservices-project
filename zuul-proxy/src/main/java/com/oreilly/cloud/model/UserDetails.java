package com.oreilly.cloud.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_details")
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_details_id")
    private int userDetailsId;
	
	@Column(name="name")
    private String name;
    
	@Column(name="surname")
    private String surname;
    
	@Column(name="birth_date")
    private LocalDateTime birthDate;

    
	public UserDetails() {
		
	}
	
	public UserDetails(int userDetailsId, String name, String surname, LocalDateTime birthDate) {
		this.userDetailsId = userDetailsId;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
	}
	
	public UserDetails(int userDetailsId, String name, String surname) {
		super();
		this.userDetailsId = userDetailsId;
		this.name = name;
		this.surname = surname;
	}

	public UserDetails(String name, String surname, LocalDateTime birthDate) {
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
	}

	public int getId() {
		return userDetailsId;
	}

	public void setId(int userDetailsId) {
		this.userDetailsId = userDetailsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "UserDetails [userDetailsId=" + userDetailsId + ", name=" + name + ", surname=" + surname + ", birthDate=" + birthDate + "]";
	}
	

}
