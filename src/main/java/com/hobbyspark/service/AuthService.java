package com.hobbyspark.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hobbyspark.config.JwtTokenProvider;
import com.hobbyspark.dto.LoginRequest;
import com.hobbyspark.dto.SignupRequest;
import com.hobbyspark.dto.TokenResponse;
import com.hobbyspark.entity.User;
import com.hobbyspark.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtProvider;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder, JwtTokenProvider jwtProvider) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    public TokenResponse signup(SignupRequest req) {
        if (userRepo.findByLogin(req.login()).isPresent()) {
            throw new RuntimeException("Логин занят");
        }
        User user = new User();
        user.setLogin(req.login());
        user.setPassword(encoder.encode(req.password()));
        user.setName(req.name());
        user = userRepo.save(user);
        String token = jwtProvider.createToken(user.getId(), user.getLogin());
        return new TokenResponse(token, user.getId(), false, user.getName());
    }

    public TokenResponse login(LoginRequest req) {
        User user = userRepo.findByLogin(req.login())
                .orElseThrow(() -> new RuntimeException("Неверный логин или пароль"));
        if (!encoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Неверный логин или пароль");
        }
        String token = jwtProvider.createToken(user.getId(), user.getLogin());
        return new TokenResponse(token, user.getId(), user.isInterestsSelected(), user.getName());
    }
}