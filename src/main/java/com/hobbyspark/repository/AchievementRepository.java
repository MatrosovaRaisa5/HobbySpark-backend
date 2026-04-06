package com.hobbyspark.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.model.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, UUID> {
}