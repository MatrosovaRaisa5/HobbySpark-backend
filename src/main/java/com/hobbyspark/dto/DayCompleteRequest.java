package com.hobbyspark.dto;

public class DayCompleteRequest {
    private Integer mood;
    private String note;
    private String photoBase64;

    public Integer getMood() { return mood; }
    public void setMood(Integer mood) { this.mood = mood; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getPhotoBase64() { return photoBase64; }
    public void setPhotoBase64(String photoBase64) { this.photoBase64 = photoBase64; }
}