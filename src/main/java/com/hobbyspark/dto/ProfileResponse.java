package com.hobbyspark.dto;

public record ProfileResponse(Long id, String login, String name, boolean interestsSelected) {}