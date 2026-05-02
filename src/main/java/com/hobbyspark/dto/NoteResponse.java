package com.hobbyspark.dto;

import java.time.LocalDateTime;

public record NoteResponse(String title, String text, String image, LocalDateTime date, String challengeTitle) {}