package com.hobbyspark.dto;

public record UserChallengeResponse(Long challengeId, String title, int currentDay, int totalDays, boolean completed) {}