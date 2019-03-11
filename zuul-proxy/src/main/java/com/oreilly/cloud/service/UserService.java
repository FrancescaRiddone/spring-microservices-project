package com.oreilly.cloud.service;

import java.util.List;

import com.oreilly.cloud.model.ApplicationUser;
import com.oreilly.cloud.object.BirthDate;
import com.oreilly.cloud.object.UserCreationRequest;
import com.oreilly.cloud.object.UserResource;

public interface UserService {
	
	public UserResource saveUser(UserCreationRequest userCreationRequest);
	
	public ApplicationUser getUser(int userId);
	
	public UserResource getUserResource(int userId);
	
	public void changeUsername(int userId, String oldUsername, String newUsername);
	
	public void changePassword(int userId, String oldPassword, String newPassword);
	
	public void changeName(int userId, String newName);
	
	public void changeSurname(int userId, String newSurname);
	
	public void changeBirthDate(int userId, BirthDate birthDate);
	
	
	public List<ApplicationUser> getUsers();

}
