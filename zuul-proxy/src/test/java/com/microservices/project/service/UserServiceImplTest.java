package com.microservices.project.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import com.microservices.project.object.UserResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.microservices.project.exception.ResourceNotFoundException;
import com.microservices.project.exception.ValidateException;
import com.microservices.project.model.ApplicationUser;
import com.microservices.project.model.UserDetails;
import com.microservices.project.repository.UserRepository;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	
	@InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    
    
    /*
	 * TESTS on method ApplicationUser getUser(String username) of UserService
	 */
    @Test
    public void findUserWithUserId() {
        assertNotNull(userService);
        int userId = 2;
        
        when(userRepository.findById(userId)).thenReturn(createOptionalApplicationUser());
        ApplicationUser user = userService.getUser(userId);

        assertEquals(user.getUserId(), 2);
        assertEquals(user.getUsername(), "elisabianchi@gmail.com");
        assertEquals(user.getDetails().getName(), "Elisa");
        assertEquals(user.getDetails().getSurname(), "Bianchi");
    }
    
    @Test(expected = ValidateException.class)
    public void userNotFoundWithInvalidUserId() {
        assertNotNull(userService);
        int userId = -1;
        
        userService.getUser(userId);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void userNotFoundWithNotPresentUserId() {
        assertNotNull(userService);
        int userId = 10000;
        
        userService.getUser(userId);
    }
    
    /*
	 * TESTS on method UserResource getUserResource(String username) of UserService
	 */
    @Test
    public void findUserResourceWithUserId() {
        assertNotNull(userService);
        int userId = 2;
        
        when(userRepository.findById(userId)).thenReturn(createOptionalApplicationUser());
        UserResource user = userService.getUserResource(userId);

        assertEquals(user.getId(), 2);
        assertEquals(user.getUsername(), "elisabianchi@gmail.com");
        assertEquals(user.getName(), "Elisa");
        assertEquals(user.getSurname(), "Bianchi");
    }
    
    @Test(expected = ValidateException.class)
    public void userResourceNotFoundWithInvalidUserId() {
        assertNotNull(userService);
        int userId = -1;
        
        userService.getUser(userId);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void userResourceNotFoundWithNotPresentUserId() {
        assertNotNull(userService);
        int userId = 10000;
        
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

    private Optional<ApplicationUser> createOptionalApplicationUser(){
        return Optional.of(createApplicationUser());
    }
	
	
}
