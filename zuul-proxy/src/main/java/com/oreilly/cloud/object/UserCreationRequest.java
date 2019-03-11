package com.oreilly.cloud.object;

public class UserCreationRequest {
	
	private String username;
    
	private String password;
	
	private String name;
    
    private String surname;
	
	private BirthDate birthDate;
	
	
	public UserCreationRequest() {
		
	}

	public UserCreationRequest(String username, String password, String name, String surname, BirthDate birthDate) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
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

	public BirthDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(BirthDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "UserCreationRequest [username=" + username + ", password=" + password + ", name=" + name + ", surname="
				+ surname + ", birthDate=" + birthDate + "]";
	}
	
	
}
