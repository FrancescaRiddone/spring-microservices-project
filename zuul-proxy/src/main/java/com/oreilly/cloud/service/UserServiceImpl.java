package com.oreilly.cloud.service;

import static com.oreilly.cloud.service.Converter.convertInUserResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.cloud.exception.PasswordValidationException;
import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.UsernameValidationException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.ApplicationUser;
import com.oreilly.cloud.model.UserDetails;
import com.oreilly.cloud.object.BirthDate;
import com.oreilly.cloud.object.UserCreationRequest;
import com.oreilly.cloud.object.UserResource;
import com.oreilly.cloud.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	// DA RIMUOVERE --------------------------------------------------------------------------------------------------------
	
	@Override
	public List<ApplicationUser> getUsers() {
		List<ApplicationUser> users = userRepository.findAll();
		
		return users;
	}
	//-----------------------------------------------------------------------------------------------------------------------

	@Override
	@Transactional
	public UserResource saveUser(UserCreationRequest userCreationRequest) throws ValidateException {
		checkParamForSaveUser(userCreationRequest);
		
		userCreationRequest.setPassword(bCryptPasswordEncoder.encode(userCreationRequest.getPassword()));
		LocalDateTime birthDate = convertInLocalDateTime(userCreationRequest.getBirthDate());
		
		ApplicationUser user = new ApplicationUser(userCreationRequest.getUsername(), userCreationRequest.getPassword());
		UserDetails userDetails = new UserDetails(userCreationRequest.getName(), userCreationRequest.getSurname(), birthDate);
		user.setDetails(userDetails);
		
		UserResource userResource = convertInUserResource(userRepository.save(user));
		
		return userResource;
	}
	
	@Override
	@Transactional
	public ApplicationUser getUser(int userId) throws ValidateException, ResourceNotFoundException {
		checkGetUserParam(userId);
		
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
		
		UserResource userResource = convertInUserResource(theUser);
		
		return userResource;
	}
	
	@Override
	@Transactional
	public void changeUsername(int userId, String oldUsername, String newUsername) throws ValidateException, ResourceNotFoundException, PasswordValidationException {
		checkChangeUsernameOrPasswordParams(userId, oldUsername, newUsername);
		
		ApplicationUser theUser = getUser(userId);
		if(!oldUsername.equals(theUser.getUsername()) || userRepository.findByUsername(newUsername) == null) {
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
	public void changeBirthDate(int userId, BirthDate birthDate) {
		checkChangeBirthDateParams(userId, birthDate);
		
		ApplicationUser theUser = getUser(userId);
		
		theUser.getDetails().setBirthDate(convertInLocalDateTime(birthDate));
		userRepository.save(theUser);
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
		LocalDateTime date = LocalDateTime.of(birthDate.getYear(), birthDate.getMonth(), birthDate.getDay(), 0, 0);
		
		return date;
	}
	
	private void checkGetUserParam(int userId) throws ValidateException {
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
