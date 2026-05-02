package com.hobbyspark.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "challenges")
public class Challenge {

    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Interest category;

    private int difficulty;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("day ASC")
    private List<WeeklyProgramDay> weeklyProgram = new ArrayList<>();

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallengeMaterial> materials = new ArrayList<>();

    // Геттеры и сеттеры (все поля)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Interest getCategory() { return category; }
    public void setCategory(Interest category) { this.category = category; }
    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
    public List<WeeklyProgramDay> getWeeklyProgram() { return weeklyProgram; }
    public void setWeeklyProgram(List<WeeklyProgramDay> weeklyProgram) { this.weeklyProgram = weeklyProgram; }
    public List<ChallengeMaterial> getMaterials() { return materials; }
    public void setMaterials(List<ChallengeMaterial> materials) { this.materials = materials; }
}