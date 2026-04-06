package com.hobbyspark.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyspark.config.JwtUtil;
import com.hobbyspark.dto.UserResponse;
import com.hobbyspark.model.User;
import com.hobbyspark.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    private User getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        UUID userId = jwtUtil.extractUserId(token);
        return userService.findById(userId);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(HttpServletRequest request) {
        User user = getCurrentUser(request);
        return ResponseEntity.ok(userService.toResponse(user));
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> updates,
                                           HttpServletRequest request) {
        User user = getCurrentUser(request);
        String name = (String) updates.get("name");
        String avatarUrl = (String) updates.get("avatar_url");
        String bio = (String) updates.get("bio");
        userService.updateProfile(user.getId(), name, avatarUrl, bio);
        return ResponseEntity.ok().build();
    }
}