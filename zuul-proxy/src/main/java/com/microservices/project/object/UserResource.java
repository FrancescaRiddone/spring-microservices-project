package com.microservices.project.object;

public class UserResource {

	private int id;
	
	private String username;
	
	private String name;
	
	private String surname;
	
	private BirthDate birthDate;

	
	public UserResource() {
		
	}

	public UserResource(int id, String username, String name, String surname, BirthDate birthDate) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		return "UserResource [id=" + id + ", username=" + username + ", name=" + name + ", surname=" + surname + ", birthDate="
				+ birthDate + "]";
	}

}
