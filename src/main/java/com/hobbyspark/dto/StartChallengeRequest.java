package com.hobbyspark.dto;

import java.util.UUID;

public class StartChallengeRequest {
    private UUID challengeId;

    public StartChallengeRequest() {}

    public UUID getChallengeId() { return challengeId; }
    public void setChallengeId(UUID challengeId) { this.challengeId = challengeId; }
}