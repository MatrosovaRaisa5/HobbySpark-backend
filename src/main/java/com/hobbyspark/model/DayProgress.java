package com.hobbyspark.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "day_progress")
public class DayProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_challenge_id")
    private UserChallenge userChallenge;

    private int dayNumber; // день челленджа (1..totalDays)

    private boolean completed; // завершён ли этот день

    private String note; // текст заметки

    private String mood; // эмодзи или текстовое описание настроения

    @Column(name = "completed_at")
    private LocalDate completedAt; // дата выполнения

    public DayProgress() {}

    // геттеры и сеттеры
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UserChallenge getUserChallenge() { return userChallenge; }
    public void setUserChallenge(UserChallenge userChallenge) { this.userChallenge = userChallenge; }

    public int getDayNumber() { return dayNumber; }
    public void setDayNumber(int dayNumber) { this.dayNumber = dayNumber; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }

    public LocalDate getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDate completedAt) { this.completedAt = completedAt; }
}