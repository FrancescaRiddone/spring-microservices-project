package com.oreilly.cloud.service;


import com.oreilly.cloud.model.ApplicationUser;
import com.oreilly.cloud.object.BirthDate;
import com.oreilly.cloud.object.UserCreationRequest;
import com.oreilly.cloud.object.UserResource;

public interface UserService {
	
	public UserResource saveUser(UserCreationRequest userCreationRequest);
	
	public ApplicationUser getUser(String username);
	
	public UserResource getUserResource(String username);
	
	public void changeUsername(String username, String oldUsername, String newUsername);
	
	public void changePassword(String username, String oldPassword, String newPassword);
	
	public void changeName(String username, String newName);
	
	public void changeSurname(String username, String newSurname);
	
	public void changeBirthDate(String username, BirthDate birthDate);
	
	public void deleteUser(String username);

}
