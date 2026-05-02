package com.hobbyspark.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "challenge_materials")
public class ChallengeMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Column(name = "material_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Challenge getChallenge() {
        return challenge;
    }
    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}