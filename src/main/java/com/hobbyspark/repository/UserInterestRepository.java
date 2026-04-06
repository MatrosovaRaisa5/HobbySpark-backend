package com.hobbyspark.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.model.User;
import com.hobbyspark.model.UserInterest;

public interface UserInterestRepository extends JpaRepository<UserInterest, UUID> {
    List<UserInterest> findByUser(User user);
    void deleteByUser(User user);
}