package com.hobbyspark.dto;

import java.util.UUID;

public class CompleteDayRequest {
    private UUID challengeId;
    private int day;
    private String note;
    private String mood;

    public CompleteDayRequest() {}

    public UUID getChallengeId() { return challengeId; }
    public void setChallengeId(UUID challengeId) { this.challengeId = challengeId; }

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }
}