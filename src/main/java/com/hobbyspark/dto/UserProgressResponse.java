package com.hobbyspark.dto;

import java.util.UUID;

public class UserProgressResponse {
    private UUID challengeId;
    private String challengeTitle;
    private int currentDay;
    private int totalDays;
    private boolean completed;

    // геттеры, сеттеры, конструктор
    public UserProgressResponse() {}

    public UUID getChallengeId() { return challengeId; }
    public void setChallengeId(UUID challengeId) { this.challengeId = challengeId; }

    public String getChallengeTitle() { return challengeTitle; }
    public void setChallengeTitle(String challengeTitle) { this.challengeTitle = challengeTitle; }

    public int getCurrentDay() { return currentDay; }
    public void setCurrentDay(int currentDay) { this.currentDay = currentDay; }

    public int getTotalDays() { return totalDays; }
    public void setTotalDays(int totalDays) { this.totalDays = totalDays; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}