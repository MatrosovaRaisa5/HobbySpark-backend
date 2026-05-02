package com.hobbyspark.dto;

import java.util.List;

public record DayTaskResponse(
        Long challengeId,
        int day,
        String title,
        String intro,
        List<MaterialDto> materials,
        List<StepDto> steps,
        List<TipDto> tips,
        List<String> questions,
        List<GalleryItemDto> gallery
) {}