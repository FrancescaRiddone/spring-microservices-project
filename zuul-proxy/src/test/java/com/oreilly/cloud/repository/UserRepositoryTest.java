package com.oreilly.cloud.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oreilly.cloud.model.ApplicationUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
	
	@Autowired
    private UserRepository userRepository;
	
	
	/*
	 * TESTS on method ApplicationUser findByUsername(String username) of UserRepository
	 */
	
	@Test
	public void findApplicationUserWithValidUsername() {
		assertNotNull(userRepository);
		String username = "elisabianchi@gmail.com";
		
		ApplicationUser user = userRepository.findByUsername(username);
		
		assertEquals(user.getUserId(), 2);
		assertEquals(user.getUsername(), "elisabianchi@gmail.com");
		assertEquals(user.getDetails().getName(), "Elisa");
		assertEquals(user.getDetails().getSurname(), "Bianchi");
	}
	
	@Test
	public void applicationUserNotFoundWithNotPresentUsername() {
		assertNotNull(userRepository);
		String username = "gennarino@gmail.com";
		
		ApplicationUser user = userRepository.findByUsername(username);
		
		assertTrue(user == null);
	}
	

}
