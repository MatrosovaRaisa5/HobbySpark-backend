package com.hobbyspark.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.entity.DayTask;

public interface DayTaskRepository extends JpaRepository<DayTask, Long> {
    Optional<DayTask> findByChallengeIdAndDay(Long challengeId, int day);
}