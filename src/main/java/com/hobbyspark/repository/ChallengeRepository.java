package com.hobbyspark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.entity.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}