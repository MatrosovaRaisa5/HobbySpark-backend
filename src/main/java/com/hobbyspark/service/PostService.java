package com.hobbyspark.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hobbyspark.dto.PostRequest;
import com.hobbyspark.dto.PostResponse;
import com.hobbyspark.dto.UserResponse;
import com.hobbyspark.model.Post;
import com.hobbyspark.model.User;
import com.hobbyspark.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Transactional
    public Post createPost(User author, PostRequest request) {
        Post post = new Post();
        post.setText(request.getText());
        if (request.getMedias() != null) {
            post.setMedias(request.getMedias().toArray(String[]::new));
        } else {
            post.setMedias(new String[0]);
        }
        post.setDisableComments(request.isDisableComments());
        post.setAuthor(author);
        post.setLikesCount(0);
        return postRepository.save(post);
    }

    public Page<Post> getFeed(int page, int size) {
        return postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page - 1, size));
    }

    public Page<Post> getUserPosts(UUID userId, int page, int size) {
        return postRepository.findByAuthorIdOrderByCreatedAtDesc(userId, PageRequest.of(page - 1, size));
    }

    public PostResponse toResponse(Post post) {
        UserResponse authorResp = userService.toResponse(post.getAuthor());
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setText(post.getText());
        response.setMedias(post.getMedias() != null ? List.of(post.getMedias()) : List.of());
        response.setDisableComments(post.isDisableComments());
        response.setAuthor(authorResp);
        response.setLikesCount(post.getLikesCount());
        response.setCreatedAt(post.getCreatedAt());
        return response;
    }
}