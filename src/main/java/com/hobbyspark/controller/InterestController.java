package com.hobbyspark.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyspark.config.JwtUtil;
import com.hobbyspark.dto.UserInterestsRequest;
import com.hobbyspark.model.User;
import com.hobbyspark.service.InterestService;
import com.hobbyspark.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/interests")
public class InterestController {

    private final InterestService interestService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public InterestController(InterestService interestService,
                              UserService userService,
                              JwtUtil jwtUtil) {
        this.interestService = interestService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    private User getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        UUID userId = jwtUtil.extractUserId(token);
        return userService.findById(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllInterests() {
        // возвращаем список всех возможных интересов (например, для страницы выбора)
        return ResponseEntity.ok(List.of("Творчество", "Спорт и Фитнес", "Технологии", "Языки", "Кулинария", "Сообщество"));
    }

    @GetMapping("/me")
    public ResponseEntity<List<String>> getMyInterests(HttpServletRequest request) {
        User user = getCurrentUser(request);
        return ResponseEntity.ok(interestService.getUserInterests(user));
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMyInterests(@RequestBody UserInterestsRequest request,
                                               HttpServletRequest servletRequest) {
        User user = getCurrentUser(servletRequest);
        interestService.saveUserInterests(user, request.getInterestNames());
        return ResponseEntity.ok().build();
    }
}