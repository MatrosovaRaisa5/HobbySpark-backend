package com.hobbyspark.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyspark.config.JwtUtil;
import com.hobbyspark.dto.PostRequest;
import com.hobbyspark.dto.PostResponse;
import com.hobbyspark.model.User;
import com.hobbyspark.service.PostService;
import com.hobbyspark.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public PostController(PostService postService, UserService userService, JwtUtil jwtUtil) {
        this.postService = postService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    private User getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        UUID userId = jwtUtil.extractUserId(token);
        return userService.findById(userId);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request,
                                                   HttpServletRequest servletRequest) {
        User author = getCurrentUser(servletRequest);
        var post = postService.createPost(author, request);
        return ResponseEntity.ok(postService.toResponse(post));
    }

    @GetMapping("/feed")
    public ResponseEntity<Page<PostResponse>> getFeed(@RequestParam(defaultValue = "1") int number,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      HttpServletRequest request) {
        getCurrentUser(request); // just validate token
        var page = postService.getFeed(number, size);
        return ResponseEntity.ok(page.map(postService::toResponse));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<PostResponse>> getMyPosts(@RequestParam(defaultValue = "1") int number,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         HttpServletRequest request) {
        User user = getCurrentUser(request);
        var page = postService.getUserPosts(user.getId(), number, size);
        return ResponseEntity.ok(page.map(postService::toResponse));
    }
}