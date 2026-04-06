package com.hobbyspark.dto;

import java.util.UUID;

public class ChallengeResponse {
    private UUID id;
    private String title;
    private String imageUrl;
    private int difficulty;
    private int totalDays;
    private String category;

    // геттеры, сеттеры, конструктор
    public ChallengeResponse() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public int getTotalDays() { return totalDays; }
    public void setTotalDays(int totalDays) { this.totalDays = totalDays; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}