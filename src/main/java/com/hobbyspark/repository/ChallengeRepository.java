package com.hobbyspark.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.model.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, UUID> {
    Page<Challenge> findByCategory(String category, Pageable pageable);
    Page<Challenge> findByDifficulty(int difficulty, Pageable pageable);
}