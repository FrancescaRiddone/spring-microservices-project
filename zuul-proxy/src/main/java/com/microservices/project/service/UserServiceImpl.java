package com.microservices.project.service;

import static com.microservices.project.service.Converter.convertInUserResource;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.project.exception.PasswordValidationException;
import com.microservices.project.exception.ResourceNotFoundException;
import com.microservices.project.exception.UsernameValidationException;
import com.microservices.project.exception.ValidateException;
import com.microservices.project.model.ApplicationUser;
import com.microservices.project.model.UserDetails;
import com.microservices.project.object.BirthDate;
import com.microservices.project.object.UserCreationRequest;
import com.microservices.project.object.UserResource;
import com.microservices.project.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	@Transactional
	public UserResource saveUser(UserCreationRequest userCreationRequest) throws ValidateException {
		checkParamForSaveUser(userCreationRequest);
		
		userCreationRequest.setPassword(bCryptPasswordEncoder.encode(userCreationRequest.getPassword()));
		LocalDateTime birthDate = convertInLocalDateTime(userCreationRequest.getBirthDate());
		
		ApplicationUser user = new ApplicationUser(userCreationRequest.getUsername(), userCreationRequest.getPassword());
		UserDetails userDetails = new UserDetails(userCreationRequest.getName(), userCreationRequest.getSurname(), birthDate);
		user.setDetails(userDetails);
		
		return convertInUserResource(userRepository.save(user));
	}
	
	@Override
	@Transactional
	public ApplicationUser getUser(int userId) throws ValidateException, ResourceNotFoundException {
		checkUserParam(userId);
		
		Optional<ApplicationUser> theUser = userRepository.findById(userId);
		if(!theUser.isPresent()) {
			throw new ResourceNotFoundException();
		}
		
		return theUser.get();
	}
	
	@Override
	@Transactional
	public UserResource getUserResource(int userId) throws ValidateException, ResourceNotFoundException {
		ApplicationUser theUser = getUser(userId);
		
		return convertInUserResource(theUser);
	}
	
	@Override
	@Transactional
	public void changeUsername(int userId, String oldUsername, String newUsername) throws ValidateException, UsernameValidationException{
		checkChangeUsernameOrPasswordParams(userId, oldUsername, newUsername);
		
		ApplicationUser theUser = getUser(userId);
		if(!oldUsername.equals(theUser.getUsername()) || userRepository.findByUsername(newUsername) != null) {
			throw new UsernameValidationException();
		}
		
		theUser.setUsername(newUsername);
		userRepository.save(theUser);
	}
	
	@Override
	@Transactional
	public void changePassword(int userId, String oldPassword, String newPassword) throws ValidateException, ResourceNotFoundException, PasswordValidationException {
		checkChangeUsernameOrPasswordParams(userId, oldPassword, newPassword);
		
		ApplicationUser theUser = getUser(userId);
		if(!bCryptPasswordEncoder.matches(oldPassword, theUser.getPassword())) {
			throw new PasswordValidationException();
		}
		
		theUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
		userRepository.save(theUser);
	}
	
	@Override
	@Transactional
	public void changeName(int userId, String newName) throws ValidateException, ResourceNotFoundException {
		checkChangeNameOrSurnameParams(userId, newName);
		
		ApplicationUser theUser = getUser(userId);
		
		theUser.getDetails().setName(newName);
		userRepository.save(theUser);
	}
	
	@Override
	@Transactional
	public void changeSurname(int userId, String newSurname) throws ValidateException, ResourceNotFoundException {
		checkChangeNameOrSurnameParams(userId, newSurname);
		
		ApplicationUser theUser = getUser(userId);
		
		theUser.getDetails().setSurname(newSurname);
		userRepository.save(theUser);
	}
	
	@Override
	@Transactional
	public void changeBirthDate(int userId, BirthDate birthDate) throws ValidateException, ResourceNotFoundException {
		checkChangeBirthDateParams(userId, birthDate);
		
		ApplicationUser theUser = getUser(userId);
		
		theUser.getDetails().setBirthDate(convertInLocalDateTime(birthDate));
		userRepository.save(theUser);
	}
	
	@Override
	@Transactional
	public void deleteUser(int userId) throws ValidateException, ResourceNotFoundException {
		ApplicationUser user = getUser(userId);
		
		userRepository.deleteById(user.getUserId());
	}

	
	private void checkParamForSaveUser(UserCreationRequest userCreationRequest) throws ValidateException {
		if(userCreationRequest == null) {
			throw new ValidateException();
		}
		if(userCreationRequest.getUsername() == null || userCreationRequest.getPassword() == null ||
				userCreationRequest.getName() == null || userCreationRequest.getSurname() == null ||
				userCreationRequest.getBirthDate() == null) {
			
			throw new ValidateException();
		}
		if(this.userRepository.findByUsername(userCreationRequest.getUsername()) != null) {
			throw new ValidateException();
		}
		
		if(userCreationRequest.getUsername().equals("") || userCreationRequest.getPassword().equals("") ||
				userCreationRequest.getName().equals("") || userCreationRequest.getSurname().equals("") ||
				userCreationRequest.getBirthDate().getDay() < 1 || userCreationRequest.getBirthDate().getDay() > 31 || 
				userCreationRequest.getBirthDate().getMonth() < 1 || userCreationRequest.getBirthDate().getMonth() > 12 ||
				userCreationRequest.getBirthDate().getYear() < 1900 || userCreationRequest.getBirthDate().getYear() > 2001) {
			
			throw new ValidateException();
		}
	}
	
	private LocalDateTime convertInLocalDateTime(BirthDate birthDate) {
		return LocalDateTime.of(birthDate.getYear(), birthDate.getMonth(), birthDate.getDay(), 0, 0);
	}
	
	private void checkUserParam(int userId) throws ValidateException {
		if(userId < 1) {
			throw new ValidateException();
		}
	}
	
	private void checkChangeUsernameOrPasswordParams(int userId, String oldValue, String newValue) throws ValidateException {
		if(userId < 1 || oldValue == null || newValue == null) {
			throw new ValidateException();
		}
		if(oldValue.equals("") || newValue.equals("")) {
			throw new ValidateException();
		}
	}
	
	private void checkChangeNameOrSurnameParams(int userId, String newValue) throws ValidateException {
		if(userId < 1 || newValue == null) {
			throw new ValidateException();
		}
		if(newValue.equals("")) {
			throw new ValidateException();
		}
	}
	
	private void checkChangeBirthDateParams(int userId, BirthDate newDate) throws ValidateException {
		if(userId < 1 || newDate == null) {
			throw new ValidateException();
		}
		if(newDate.getDay() < 1 || newDate.getDay() > 31 || 
				newDate.getMonth() < 1 || newDate.getMonth() > 12 ||
				newDate.getYear() < 1900 || newDate.getYear() > 2001) {
			throw new ValidateException();
		}
	}
	

}
