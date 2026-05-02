package com.hobbyspark.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hobbyspark.dto.InterestsRequest;
import com.hobbyspark.entity.Interest;
import com.hobbyspark.entity.User;
import com.hobbyspark.repository.InterestRepository;
import com.hobbyspark.repository.UserRepository;

@Service
public class InterestService {

    private final InterestRepository interestRepo;
    private final UserRepository userRepo;

    public InterestService(InterestRepository interestRepo, UserRepository userRepo) {
        this.interestRepo = interestRepo;
        this.userRepo = userRepo;
    }

    public Set<Interest> getAll() {
        return interestRepo.findAll().stream().collect(Collectors.toSet());
    }

    @Transactional
    public void saveUserInterests(Long userId, InterestsRequest req) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Set<Interest> interests = req.interestIds().stream()
                .map(id -> interestRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Интерес не найден: " + id)))
                .collect(Collectors.toSet());
        user.setInterests(interests);
        user.setInterestsSelected(true);
        userRepo.save(user);
    }
}