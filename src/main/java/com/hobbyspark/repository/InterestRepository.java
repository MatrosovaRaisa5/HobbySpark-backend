package com.hobbyspark.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.model.Interest;

public interface InterestRepository extends JpaRepository<Interest, UUID> {
    Optional<Interest> findByName(String name);
}