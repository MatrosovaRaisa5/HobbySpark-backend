package com.hobbyspark.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_challenges")
public class UserChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Column(name = "start_date", insertable = false, updatable = false)
    private LocalDateTime startDate;

    @Column(name = "current_day")
    private int currentDay = 1;

    @Column(name = "total_days", nullable = false)
    private int totalDays;

    @Column(nullable = false)
    private boolean completed = false;

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Challenge getChallenge() {
        return challenge;
    }
    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public int getCurrentDay() {
        return currentDay;
    }
    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }
    public int getTotalDays() {
        return totalDays;
    }
    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}