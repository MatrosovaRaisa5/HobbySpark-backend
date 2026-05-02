package com.hobbyspark.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hobbyspark.dto.NoteResponse;
import com.hobbyspark.dto.ProfileResponse;
import com.hobbyspark.dto.UserChallengeResponse;
import com.hobbyspark.entity.DayCompletion;
import com.hobbyspark.entity.User;
import com.hobbyspark.repository.DayCompletionRepository;
import com.hobbyspark.repository.UserChallengeRepository;
import com.hobbyspark.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final UserChallengeRepository userChallengeRepo;
    private final DayCompletionRepository dayCompletionRepo;

    public UserService(UserRepository userRepo,
                       UserChallengeRepository userChallengeRepo,
                       DayCompletionRepository dayCompletionRepo) {
        this.userRepo = userRepo;
        this.userChallengeRepo = userChallengeRepo;
        this.dayCompletionRepo = dayCompletionRepo;
    }

    public ProfileResponse getProfile(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return new ProfileResponse(user.getId(), user.getLogin(), user.getName(), user.isInterestsSelected());
    }

    @Transactional
    public void updateName(Long userId, String name) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        user.setName(name);
        userRepo.save(user);
    }

    @Transactional
    public void deleteAccount(Long userId) {
        userRepo.deleteById(userId);
    }

    public UserChallengeResponse getActiveChallenge(Long userId) {
        return userChallengeRepo.findByUserIdAndCompletedFalse(userId)
                .map(uc -> new UserChallengeResponse(
                        uc.getChallenge().getId(),
                        uc.getChallenge().getTitle(),
                        uc.getCurrentDay(),
                        uc.getTotalDays(),
                        uc.isCompleted()
                ))
                .orElse(null);
    }

    public List<NoteResponse> getNotes(Long userId) {
    List<DayCompletion> completions = dayCompletionRepo.findByUserIdOrderByCompletedAtDesc(userId);
    return completions.stream()
            .map(dc -> {
                String challengeTitle = dc.getChallenge().getTitle();
                return new NoteResponse(
                        "День " + dc.getDayNumber(),
                        dc.getNoteText() != null ? dc.getNoteText() : "",
                        dc.getPhotoPath(),
                        dc.getCompletedAt(),
                        challengeTitle
                );
            })
            .collect(Collectors.toList());
}
}