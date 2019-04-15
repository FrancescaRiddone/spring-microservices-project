package com.microservices.project.repository;


import com.microservices.project.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
	
	public ApplicationUser findByUsername(String username);

}
