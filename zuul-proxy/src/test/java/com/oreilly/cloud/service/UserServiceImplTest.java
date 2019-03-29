package com.oreilly.cloud.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

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
	 * TESTS on method ApplicationUser getUser(String username) of UserService
	 */
    @Test
    public void findUserWithUsername() {
        assertNotNull(userService);
        String username = "elisabianchi@gmail.com";
        
        when(userRepository.findByUsername(username)).thenReturn(createApplicationUser());
        ApplicationUser user = userService.getUser(username);

        assertEquals(user.getUsername(), "elisabianchi@gmail.com");
        assertEquals(user.getDetails().getName(), "Elisa");
        assertEquals(user.getDetails().getSurname(), "Bianchi");
    }
    
    @Test(expected = ValidateException.class)
    public void userNotFoundWithInvalidUsername() {
        assertNotNull(userService);
        String username = null;
        
        userService.getUser(username);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void userNotFoundWithNotPresentUsername() {
        assertNotNull(userService);
        String username = "elisaneri@gmail.com";
        
        when(userRepository.findByUsername(username)).thenReturn(null);
        userService.getUser(username);
    }
    
    /*
	 * TESTS on method UserResource getUserResource(String username) of UserService
	 */
    @Test
    public void findUserResourceWithUsername() {
        assertNotNull(userService);
        String username = "elisabianchi@gmail.com";
        
        when(userRepository.findByUsername(username)).thenReturn(createApplicationUser());
        UserResource user = userService.getUserResource(username);

        assertEquals(user.getUsername(), "elisabianchi@gmail.com");
        assertEquals(user.getName(), "Elisa");
        assertEquals(user.getSurname(), "Bianchi");
    }
    
    @Test(expected = ValidateException.class)
    public void userResourceNotFoundWithInvalidUsername() {
        assertNotNull(userService);
        String username = "";
        
        userService.getUser(username);
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void userResourceNotFoundWithNotPresentUsername() {
        assertNotNull(userService);
        String username = "elisaneri@gmail.com";
        
        when(userRepository.findByUsername(username)).thenReturn(null);
        userService.getUser(username);
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
	
	
}
