package com.hobbyspark.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PostResponse {
    private UUID id;
    private String text;
    private List<String> medias;
    private boolean disableComments;
    private UserResponse author;
    private int likesCount;
    private LocalDateTime createdAt;

    public PostResponse() {}

    public PostResponse(UUID id, String text, List<String> medias, boolean disableComments,
                        UserResponse author, int likesCount, LocalDateTime createdAt) {
        this.id = id;
        this.text = text;
        this.medias = medias;
        this.disableComments = disableComments;
        this.author = author;
        this.likesCount = likesCount;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<String> getMedias() { return medias; }
    public void setMedias(List<String> medias) { this.medias = medias; }

    public boolean isDisableComments() { return disableComments; }
    public void setDisableComments(boolean disableComments) { this.disableComments = disableComments; }

    public UserResponse getAuthor() { return author; }
    public void setAuthor(UserResponse author) { this.author = author; }

    public int getLikesCount() { return likesCount; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}