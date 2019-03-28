package com.oreilly.cloud.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.oreilly.cloud.model.ApplicationUser;
import com.oreilly.cloud.model.UserDetails;
import com.oreilly.cloud.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {
	
	@InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;
	
	
    /*
	 * TESTS on method UserDetails loadUserByUsername(String username) of UserDetailsService
	 */
    
    @Test
    public void UserDetailsFoundWithUsername() {
        assertNotNull(userDetailsService);
        String username = "elisabianchi@gmail.com";

        when(userRepository.findByUsername(username)).thenReturn(createApplicationUser());
        org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), username);
    }
    
    @Test(expected = UsernameNotFoundException.class)
    public void UserDetailsNotFoundWithNotPresentUsername() {
        assertNotNull(userDetailsService);
        String username = "gennarino@gmail.com";

        when(userRepository.findByUsername(username)).thenReturn(null);
        userDetailsService.loadUserByUsername(username);
    }
    
    
    private ApplicationUser createApplicationUser() {
    	UserDetails ud = new UserDetails(2, "Elisa", "Bianchi");
    	ApplicationUser user = new ApplicationUser();
    	user.setUserId(2);
    	user.setPassword("cavallo");
    	user.setUsername("elisabianchi@gmail.com");
    	user.setDetails(ud);
    	
    	return user;
    }
    

}
