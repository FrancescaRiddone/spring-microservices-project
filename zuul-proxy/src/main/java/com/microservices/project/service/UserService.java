package com.microservices.project.service;


import com.microservices.project.model.ApplicationUser;
import com.microservices.project.object.BirthDate;
import com.microservices.project.object.UserCreationRequest;
import com.microservices.project.object.UserResource;

public interface UserService {
	
	public UserResource saveUser(UserCreationRequest userCreationRequest);
	
	public ApplicationUser getUser(int userId);
	
	public UserResource getUserResource(int userId);
	
	public void changeUsername(int userId, String oldUsername, String newUsername);
	
	public void changePassword(int userId, String oldPassword, String newPassword);
	
	public void changeName(int userId, String newName);
	
	public void changeSurname(int userId, String newSurname);
	
	public void changeBirthDate(int userId, BirthDate birthDate);
	
	public void deleteUser(int userId);

}
