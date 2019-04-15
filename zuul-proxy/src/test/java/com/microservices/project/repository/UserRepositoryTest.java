package com.microservices.project.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.microservices.project.model.ApplicationUser;

import java.util.Optional;

import static org.junit.Assert.*;

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
		
		assertNull(user);
	}

	/*
	 * TESTS on method Optional<ApplicationUser> findById(int userId) of UserRepository
	 */

	@Test
	public void findApplicationUserWithValidUserId() {
		assertNotNull(userRepository);
		int userId = 2;

		Optional<ApplicationUser> user = userRepository.findById(userId);

		assertTrue(user.isPresent());
		assertEquals(user.get().getUserId(), 2);
		assertEquals(user.get().getUsername(), "elisabianchi@gmail.com");
		assertEquals(user.get().getDetails().getName(), "Elisa");
		assertEquals(user.get().getDetails().getSurname(), "Bianchi");
	}

	@Test
	public void applicationUserNotFoundWithNotPresentUserId() {
		assertNotNull(userRepository);
		int userId = 20000;

		Optional<ApplicationUser> user = userRepository.findById(userId);

		assertFalse(user.isPresent());
	}


}
