package com.hobbyspark.dto;

import java.util.List;

public class PostRequest {
    private String text;
    private List<String> medias;
    private boolean disableComments;

    public PostRequest() {}

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<String> getMedias() { return medias; }
    public void setMedias(List<String> medias) { this.medias = medias; }

    public boolean isDisableComments() { return disableComments; }
    public void setDisableComments(boolean disableComments) { this.disableComments = disableComments; }
}