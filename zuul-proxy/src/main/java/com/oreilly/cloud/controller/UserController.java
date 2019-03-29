package com.oreilly.cloud.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oreilly.cloud.exception.PasswordValidationException;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.UsernameValidationException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.object.BirthDate;
import com.oreilly.cloud.object.UserCreationRequest;
import com.oreilly.cloud.object.UserResource;
import com.oreilly.cloud.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
		
	@PostMapping("/signUp")
	public UserResource signUp(@RequestBody UserCreationRequest userCreationRequest) {
		
		return userService.saveUser(userCreationRequest);
	}
	
	@GetMapping("/users/user/{username}")
	public UserResource getUser(@PathVariable String username, Authentication authentication, Principal principal) {
		
		return userService.getUserResource(username);
	}
	
	@PutMapping("/users/user/{username}/changeUsername")
	public String changeUsername(@PathVariable("username") String username, 
			@RequestParam("oldUsername") String oldUsername, @RequestParam("newUsername") String newUsername) {
		
		try {
			userService.changeUsername(username, oldUsername, newUsername);
		} catch(ValidateException ex1) {
			return "Error! Please check the inserted parameters.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check your user id.";
		} catch(UsernameValidationException ex3) {
			return "Error! Please insert the two inserted usernames.";
		} 
		
		return "Your username has been modified with success!";
	}
	
	@PutMapping("/users/user/{username}/changePassword")
	public String changePassword(@PathVariable("username") String username, 
			@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
		
		try {
			userService.changePassword(username, oldPassword, newPassword);
		} catch(ValidateException ex1) {
			return "Error! Please check the inserted parameters.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check your user id.";
		} catch(PasswordValidationException ex3) {
			return "Error! Please insert the right previous password.";
		} 
		
		return "Your password has been modified with success!";
	}
	
	@PutMapping("/users/user/{username}/changeName")
	public String changeName(@PathVariable("username") String username, @RequestParam("newName") String newName) {
		try {
			userService.changeName(username, newName);
		} catch(ValidateException ex1) {
			return "Error! Please check the inserted parameters.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check your user id.";
		} 
		
		return "Your name has been modified with success!";
	}
	
	@PutMapping("/users/user/{username}/changeSurname")
	public String changeSurname(@PathVariable("username") String username, @RequestParam("newSurname") String newSurname) {
		try {
			userService.changeSurname(username, newSurname);
		} catch(ValidateException ex1) {
			return "Error! Please check the inserted parameters.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check your user id.";
		} 
		
		return "Your surname has been modified with success!";
	}
	
	@PutMapping("/users/user/{username}/changeBirthDate")
	public String changeBirthDate(@PathVariable("username") String username, @RequestBody BirthDate birthDate) {
		try {
			userService.changeBirthDate(username, birthDate);
		} catch(ValidateException ex1) {
			return "Error! Please check the inserted parameters.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check your user id.";
		} 
		
		return "Your birth date has been modified with success!";
	}
	
	@DeleteMapping("/users/user/{username}")
	public String deleteUser(@PathVariable("username") String username) {
		try {
			userService.deleteUser(username);
		} catch(ValidateException ex1) {
			return "Error! Please check the user id to be deleted.";
		} catch(ResourceNotFoundException ex2) {
			return "Error! Please check the user id to be deleted.";
		} 
		
		return "Your account has been deleted with success!";
	}
	

}
