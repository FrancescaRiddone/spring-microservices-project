package com.oreilly.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oreilly.cloud.model.Reservation;
import com.oreilly.cloud.model.ReservationSeat;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Integer> {

}
