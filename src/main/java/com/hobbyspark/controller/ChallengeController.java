package com.hobbyspark.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyspark.dto.ChallengeResponse;
import com.hobbyspark.service.ChallengeService;

@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping
    public ResponseEntity<Page<ChallengeResponse>> getAllChallenges(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(challengeService.getAllChallenges(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChallenge(@PathVariable UUID id) {
        // возвращаем полный объект Challenge 
        return ResponseEntity.ok(challengeService.getChallengeById(id));
    }
}