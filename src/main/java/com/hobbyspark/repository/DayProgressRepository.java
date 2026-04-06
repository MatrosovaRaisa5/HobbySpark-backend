package com.hobbyspark.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.model.DayProgress;
import com.hobbyspark.model.UserChallenge;

public interface DayProgressRepository extends JpaRepository<DayProgress, UUID> {
    Optional<DayProgress> findByUserChallengeAndDayNumber(UserChallenge userChallenge, int dayNumber);
}