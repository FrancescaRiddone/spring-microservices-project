package com.microservices.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.microservices.project.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer>, QuerydslPredicateExecutor<Hotel>  {

	
}
