package com.oreilly.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oreilly.cloud.model.InvalidToken;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {

}
