package com.hobbyspark.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyspark.dto.InterestsRequest;
import com.hobbyspark.entity.Interest;
import com.hobbyspark.service.InterestService;

@RestController
@RequestMapping("/api/interests")
public class InterestController {

    private final InterestService interestService;

    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Long) {
            return (Long) auth.getPrincipal();
        }
        throw new RuntimeException("Пользователь не авторизован");
    }

    @GetMapping
    public Set<Interest> getAll() {
        // доступно всем, поэтому не требуется userId
        return interestService.getAll();
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody InterestsRequest req) {
        Long userId = getCurrentUserId();
        interestService.saveUserInterests(userId, req);
        return ResponseEntity.ok().build();
    }
}