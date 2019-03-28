package com.oreilly.cloud.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oreilly.cloud.exception.ResourceNotFoundException;
import com.oreilly.cloud.exception.ValidateException;
import com.oreilly.cloud.model.ApplicationUser;
import com.oreilly.cloud.model.UserDetails;
import com.oreilly.cloud.object.UserResource;
import com.oreilly.cloud.repository.UserRepository;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	
	@InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    
    @Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    
    /*
	 * TESTS on method ApplicationUser getUser(int userId) of UserService
	 */
    @Test
    public void findUserWithId() {
        assertNotNull(userService);
        int userId = 2;
        
        when(userRepository.findById(userId)).thenReturn(createOptionalApplicationUser());
        ApplicationUser user = userService.getUser(userId);

        assertEquals(user.getUsername(), "elisabianchi@gmail.com");
        assertEquals(user.getDetails().getName(), "Elisa");
        assertEquals(user.getDetails().getSurname(), "Bianchi");
    }
    
    @Test(expected = ValidateException.class)
    public void userNotFoundWithInvalidId() {
        assertNotNull(userService);
        int userId = -1;
        
        userService.getUser(userId);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void userNotFoundWithNotPresentId() {
        assertNotNull(userService);
        int userId = 100000;
        
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        userService.getUser(userId);
    }
    
    /*
	 * TESTS on method UserResource getUserResource(int userId) of UserService
	 */
    @Test
    public void findUserResourceWithId() {
        assertNotNull(userService);
        int userId = 2;
        
        when(userRepository.findById(userId)).thenReturn(createOptionalApplicationUser());
        UserResource user = userService.getUserResource(userId);

        assertEquals(user.getUsername(), "elisabianchi@gmail.com");
        assertEquals(user.getName(), "Elisa");
        assertEquals(user.getSurname(), "Bianchi");
    }
    
    @Test(expected = ValidateException.class)
    public void userResourceNotFoundWithInvalidId() {
        assertNotNull(userService);
        int userId = -1;
        
        userService.getUser(userId);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void userResourceNotFoundWithNotPresentId() {
        assertNotNull(userService);
        int userId = 100000;
        
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        userService.getUser(userId);
    }
    
    
    private ApplicationUser createApplicationUser() {
    	LocalDateTime date = LocalDateTime.of(1995, 10, 10, 0, 0);
    	UserDetails ud = new UserDetails(2, "Elisa", "Bianchi", date);
    	ApplicationUser user = new ApplicationUser();
    	user.setUserId(2);
    	user.setPassword("cavallo");
    	user.setUsername("elisabianchi@gmail.com");
    	user.setDetails(ud);
    	
    	return user;
    }
	
	private Optional<ApplicationUser> createOptionalApplicationUser() {
		Optional<ApplicationUser> theOptUser = Optional.of(createApplicationUser());
    	
    	return theOptUser;
	}
	
	
}
