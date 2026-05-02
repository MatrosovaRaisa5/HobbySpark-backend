package com.hobbyspark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hobbyspark.entity.Interest;

public interface InterestRepository extends JpaRepository<Interest, Long> {
}