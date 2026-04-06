package com.hobbyspark.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyspark.config.JwtUtil;
import com.hobbyspark.dto.CompleteDayRequest;
import com.hobbyspark.dto.StartChallengeRequest;
import com.hobbyspark.dto.UserProgressResponse;
import com.hobbyspark.model.User;
import com.hobbyspark.service.ProgressService;
import com.hobbyspark.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public ProgressController(ProgressService progressService,
                              UserService userService,
                              JwtUtil jwtUtil) {
        this.progressService = progressService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    private User getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        UUID userId = jwtUtil.extractUserId(token);
        return userService.findById(userId);
    }

    @PostMapping("/start")
    public ResponseEntity<?> startChallenge(@RequestBody StartChallengeRequest request,
                                            HttpServletRequest servletRequest) {
        User user = getCurrentUser(servletRequest);
        progressService.startChallenge(user.getId(), request.getChallengeId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/complete-day")
    public ResponseEntity<?> completeDay(@Valid @RequestBody CompleteDayRequest request,
                                         HttpServletRequest servletRequest) {
        User user = getCurrentUser(servletRequest);
        progressService.completeDay(user.getId(), request.getChallengeId(), request.getDay(),
                request.getNote(), request.getMood());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<UserProgressResponse>> getMyProgress(HttpServletRequest request) {
        User user = getCurrentUser(request);
        return ResponseEntity.ok(progressService.getUserProgress(user.getId()));
    }
}