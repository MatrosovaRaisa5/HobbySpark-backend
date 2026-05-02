package com.hobbyspark.dto;

import java.util.List;

public record ChallengeDetailResponse(
        Long id,
        String title,
        String image,
        String description,
        String category,
        int difficulty,
        List<WeeklyProgramDayDto> weeklyProgram,
        List<String> materials
) {}