package com.oreilly.cloud.service;

import java.time.LocalDateTime;

import com.oreilly.cloud.model.ApplicationUser;
import com.oreilly.cloud.object.BirthDate;
import com.oreilly.cloud.object.UserResource;

public class Converter {
	
	public static UserResource convertInUserResource(ApplicationUser theUser) {
		UserResource theUserResource = new UserResource(theUser.getUsername(), theUser.getDetails().getName(), 
				theUser.getDetails().getSurname(), convertInBirthDate(theUser.getDetails().getBirthDate()));
				
		return theUserResource;
	}
	
	private static BirthDate convertInBirthDate(LocalDateTime time) {
		BirthDate birthDate = new BirthDate(time.getDayOfMonth(), time.getMonthValue(), time.getYear());
		
		return birthDate;
	}
	
	
}
