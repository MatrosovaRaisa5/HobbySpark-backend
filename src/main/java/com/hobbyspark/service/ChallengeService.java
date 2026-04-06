package com.hobbyspark.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hobbyspark.dto.ChallengeResponse;
import com.hobbyspark.model.Challenge;
import com.hobbyspark.repository.ChallengeRepository;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public Page<ChallengeResponse> getAllChallenges(int page, int size) {
        return challengeRepository.findAll(PageRequest.of(page - 1, size))
                .map(this::toResponse);
    }

    public Challenge getChallengeById(UUID id) {
        return challengeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));
    }

    public ChallengeResponse toResponse(Challenge c) {
        ChallengeResponse resp = new ChallengeResponse();
        resp.setId(c.getId());
        resp.setTitle(c.getTitle());
        resp.setImageUrl(c.getImageUrl());
        resp.setDifficulty(c.getDifficulty());
        resp.setTotalDays(c.getTotalDays());
        resp.setCategory(c.getCategory());
        return resp;
    }
}