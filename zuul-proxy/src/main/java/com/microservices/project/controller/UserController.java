package com.microservices.project.controller;

import com.microservices.project.object.BirthDate;
import com.microservices.project.object.UserCreationRequest;
import com.microservices.project.object.UserResource;
import com.microservices.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.project.exception.PasswordValidationException;
import com.microservices.project.exception.ResourceNotFoundException;
import com.microservices.project.exception.UsernameValidationException;
import com.microservices.project.exception.ValidateException;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
		
	@PostMapping("/signUp")
	public UserResource signUp(@RequestBody UserCreationRequest userCreationRequest) {
		
		return userService.saveUser(userCreationRequest);
	}
	
	@GetMapping("/users/user/{userId}")
	public UserResource getUser(@PathVariable("userId") int userId) {
		
		return userService.getUserResource(userId);
	}
	
	@PutMapping("/users/user/{userId}/changeUsername")
	public String changeUsername(@PathVariable("userId") int userId,
			@RequestParam("oldUsername") String oldUsername, @RequestParam("newUsername") String newUsername) {
		
		try {
			userService.changeUsername(userId, oldUsername, newUsername);
		} catch(ValidateException ex1) {
			return "Error! Please check the inserted parameters.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check your user id.";
		} catch(UsernameValidationException ex3) {
			return "Error! Please insert the right previous password.";
		} 
		
		return "Your username has been modified with success!";
	}
	
	@PutMapping("/users/user/{userId}/changePassword")
	public String changePassword(@PathVariable("userId") int userId,
			@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
		
		try {
			userService.changePassword(userId, oldPassword, newPassword);
		} catch(ValidateException ex1) {
			return "Error! Please check the inserted parameters.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check your user id.";
		} catch(PasswordValidationException ex3) {
			return "Error! Please insert the right previous password.";
		} 
		
		return "Your password has been modified with success!";
	}
	
	@PutMapping("/users/user/{userId}/changeName")
	public String changeName(@PathVariable("userId") int userId, @RequestParam("newName") String newName) {
		try {
			userService.changeName(userId, newName);
		} catch(ValidateException ex1) {
			return "Error! Please check the inserted parameters.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check your user id.";
		} 
		
		return "Your name has been modified with success!";
	}
	
	@PutMapping("/users/user/{userId}/changeSurname")
	public String changeSurname(@PathVariable("userId") int userId, @RequestParam("newSurname") String newSurname) {
		try {
			userService.changeSurname(userId, newSurname);
		} catch(ValidateException ex1) {
			return "Error! Please check the inserted parameters.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check your user id.";
		} 
		
		return "Your surname has been modified with success!";
	}
	
	@PutMapping("/users/user/{userId}/changeBirthDate")
	public String changeBirthDate(@PathVariable("userId") int userId, @RequestBody BirthDate birthDate) {
		try {
			userService.changeBirthDate(userId, birthDate);
		} catch(ValidateException ex1) {
			return "Error! Please check the inserted parameters.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check your user id.";
		} 
		
		return "Your birth date has been modified with success!";
	}
	
	@DeleteMapping("/users/user/{userId}")
	public String deleteUser(@PathVariable("userId") int userId) {
		try {
			userService.deleteUser(userId);
		} catch(ValidateException ex1) {
			return "Error! Please check the user id to be deleted.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check the user id to be deleted.";
		} 
		
		return "Your account has been deleted with success!";
	}
	

}
