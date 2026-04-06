package com.hobbyspark.dto;

import java.util.List;

public class UserInterestsRequest {
    private List<String> interestNames; // список названий интересов

    public UserInterestsRequest() {}

    public List<String> getInterestNames() { return interestNames; }
    public void setInterestNames(List<String> interestNames) { this.interestNames = interestNames; }
}