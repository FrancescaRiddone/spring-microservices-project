package com.oreilly.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.oreilly.cloud.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer>, QuerydslPredicateExecutor<Hotel>  {

	
}
