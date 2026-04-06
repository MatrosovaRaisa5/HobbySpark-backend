package com.hobbyspark.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hobbyspark.model.Interest;
import com.hobbyspark.model.User;
import com.hobbyspark.model.UserInterest;
import com.hobbyspark.repository.InterestRepository;
import com.hobbyspark.repository.UserInterestRepository;

@Service
public class InterestService {

    private final InterestRepository interestRepository;
    private final UserInterestRepository userInterestRepository;

    public InterestService(InterestRepository interestRepository,
                           UserInterestRepository userInterestRepository) {
        this.interestRepository = interestRepository;
        this.userInterestRepository = userInterestRepository;
    }

    @Transactional
    public void saveUserInterests(User user, List<String> interestNames) {
        // Удаляем старые интересы пользователя
        userInterestRepository.deleteByUser(user);

        for (String name : interestNames) {
            Interest interest = interestRepository.findByName(name)
                    .orElseGet(() -> {
                        Interest newInterest = new Interest();
                        newInterest.setName(name);
                        return interestRepository.save(newInterest);
                    });
            UserInterest ui = new UserInterest();
            ui.setUser(user);
            ui.setInterest(interest);
            userInterestRepository.save(ui);
        }
    }

    public List<String> getUserInterests(User user) {
        return userInterestRepository.findByUser(user).stream()
                .map(ui -> ui.getInterest().getName())
                .collect(Collectors.toList());
    }

    public List<Interest> getAllInterests() {
        return interestRepository.findAll();
    }
}