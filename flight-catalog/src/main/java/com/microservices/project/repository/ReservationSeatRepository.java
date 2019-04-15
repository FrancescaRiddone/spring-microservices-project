package com.microservices.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.project.model.ReservationSeat;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Integer> {

}
