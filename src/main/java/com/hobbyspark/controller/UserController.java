package com.hobbyspark.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyspark.dto.NoteResponse;
import com.hobbyspark.dto.ProfileResponse;
import com.hobbyspark.dto.UpdateProfileRequest;
import com.hobbyspark.dto.UserChallengeResponse;
import com.hobbyspark.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ProfileResponse getProfile(@AuthenticationPrincipal Long userId) {
        return userService.getProfile(userId);
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal Long userId,
                                              @RequestBody UpdateProfileRequest req) {
        userService.updateName(userId, req.name());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal Long userId) {
        userService.deleteAccount(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/challenges")
    public ResponseEntity<UserChallengeResponse> activeChallenge(@AuthenticationPrincipal Long userId) {
        UserChallengeResponse resp = userService.getActiveChallenge(userId);
        return resp != null ? ResponseEntity.ok(resp) : ResponseEntity.noContent().build();
    }

    @GetMapping("/notes")
    public List<NoteResponse> notes(@AuthenticationPrincipal Long userId) {
        return userService.getNotes(userId);
    }
}