package com.hobbyspark.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "day_tasks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"challenge_id", "day"})
})
public class DayTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int day;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String intro;

    @Column(name = "materials_json", columnDefinition = "jsonb")
    private String materialsJson;

    @Column(name = "steps_json", columnDefinition = "jsonb")
    private String stepsJson;

    @Column(name = "tips_json", columnDefinition = "jsonb")
    private String tipsJson;

    @Column(name = "questions_json", columnDefinition = "jsonb")
    private String questionsJson;

    @Column(name = "gallery_json", columnDefinition = "jsonb")
    private String galleryJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIntro() {
        return intro;
    }
    public void setIntro(String intro) {
        this.intro = intro;
    }
    public String getMaterialsJson() {
        return materialsJson;
    }
    public void setMaterialsJson(String materialsJson) {
        this.materialsJson = materialsJson;
    }
    public String getStepsJson() {
        return stepsJson;
    }
    public void setStepsJson(String stepsJson) {
        this.stepsJson = stepsJson;
    }
    public String getTipsJson() {
        return tipsJson;
    }
    public void setTipsJson(String tipsJson) {
        this.tipsJson = tipsJson;
    }
    public String getQuestionsJson() {
        return questionsJson;
    }
    public void setQuestionsJson(String questionsJson) {
        this.questionsJson = questionsJson;
    }
    public String getGalleryJson() {
        return galleryJson;
    }
    public void setGalleryJson(String galleryJson) {
        this.galleryJson = galleryJson;
    }
    public Challenge getChallenge() {
        return challenge;
    }
    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}