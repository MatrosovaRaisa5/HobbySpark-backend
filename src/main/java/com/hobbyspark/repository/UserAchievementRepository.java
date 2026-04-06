package com.hobbyspark.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.model.User;
import com.hobbyspark.model.UserAchievement;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, UUID> {
    List<UserAchievement> findByUser(User user);
}