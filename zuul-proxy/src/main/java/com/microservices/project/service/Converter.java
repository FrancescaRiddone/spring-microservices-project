package com.microservices.project.service;

import java.time.LocalDateTime;

import com.microservices.project.model.ApplicationUser;
import com.microservices.project.object.BirthDate;
import com.microservices.project.object.UserResource;

public class Converter {
	
	public static UserResource convertInUserResource(ApplicationUser theUser) {
		UserResource theUserResource = new UserResource(theUser.getUserId(), theUser.getUsername(), theUser.getDetails().getName(),
				theUser.getDetails().getSurname(), convertInBirthDate(theUser.getDetails().getBirthDate()));
				
		return theUserResource;
	}
	
	private static BirthDate convertInBirthDate(LocalDateTime time) {
		BirthDate birthDate = new BirthDate(time.getDayOfMonth(), time.getMonthValue(), time.getYear());
		
		return birthDate;
	}
	
	
}
