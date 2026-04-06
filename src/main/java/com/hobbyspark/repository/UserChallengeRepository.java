package com.hobbyspark.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.model.Challenge;
import com.hobbyspark.model.User;
import com.hobbyspark.model.UserChallenge;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, UUID> {
    List<UserChallenge> findByUser(User user);
    Optional<UserChallenge> findByUserAndChallenge(User user, Challenge challenge);
}