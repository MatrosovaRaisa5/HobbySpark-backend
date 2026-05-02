package com.hobbyspark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.entity.DayCompletion;

public interface DayCompletionRepository extends JpaRepository<DayCompletion, Long> {
    List<DayCompletion> findByUserIdOrderByCompletedAtDesc(Long userId);
}