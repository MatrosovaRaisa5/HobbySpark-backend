package com.hobbyspark.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyspark.dto.ChallengeDetailResponse;
import com.hobbyspark.dto.ChallengeListResponse;
import com.hobbyspark.dto.DayCompleteRequest;
import com.hobbyspark.dto.DayTaskResponse;
import com.hobbyspark.service.ChallengeService;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    // Вспомогательный метод для получения userId
    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Long) {
            return (Long) auth.getPrincipal();
        }
        throw new RuntimeException("Пользователь не авторизован");
    }

    @GetMapping
    public List<ChallengeListResponse> list() {
        return challengeService.getAll();
    }

    @GetMapping("/{id}")
    public ChallengeDetailResponse detail(@PathVariable Long id) {
        return challengeService.getDetail(id);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> start(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        challengeService.startChallenge(userId, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/days/{day}")
    public DayTaskResponse dayTask(@PathVariable Long id, @PathVariable int day) {
        return challengeService.getDayTask(id, day);
    }

    @PostMapping("/{id}/days/{day}/complete")
    public ResponseEntity<Void> complete(
            @PathVariable Long id,
            @PathVariable int day,
            @RequestBody DayCompleteRequest request) {
        Long userId = getCurrentUserId();
        challengeService.completeDay(userId, id, day, request);
        return ResponseEntity.ok().build();
    }
}