package com.hobbyspark.dto;

public record TokenResponse(String token, Long userId, boolean interestsSelected, String name) {}