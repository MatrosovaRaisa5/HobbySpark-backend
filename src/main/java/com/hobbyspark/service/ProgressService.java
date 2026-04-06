package com.hobbyspark.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hobbyspark.dto.UserProgressResponse;
import com.hobbyspark.model.Challenge;
import com.hobbyspark.model.DayProgress;
import com.hobbyspark.model.User;
import com.hobbyspark.model.UserChallenge;
import com.hobbyspark.repository.DayProgressRepository;
import com.hobbyspark.repository.UserChallengeRepository;

@Service
public class ProgressService {

    private final UserChallengeRepository userChallengeRepository;
    private final DayProgressRepository dayProgressRepository;
    private final ChallengeService challengeService;
    private final UserService userService;

    public ProgressService(UserChallengeRepository userChallengeRepository,
                           DayProgressRepository dayProgressRepository,
                           ChallengeService challengeService,
                           UserService userService) {
        this.userChallengeRepository = userChallengeRepository;
        this.dayProgressRepository = dayProgressRepository;
        this.challengeService = challengeService;
        this.userService = userService;
    }

    @Transactional
    public UserChallenge startChallenge(UUID userId, UUID challengeId) {
        User user = userService.findById(userId);
        Challenge challenge = challengeService.getChallengeById(challengeId);

        // Проверяем, не начат ли уже
        var existing = userChallengeRepository.findByUserAndChallenge(user, challenge);
        if (existing.isPresent()) {
            return existing.get();
        }

        UserChallenge uc = new UserChallenge();
        uc.setUser(user);
        uc.setChallenge(challenge);
        uc.setCurrentDay(0); // ещё не начат
        uc.setCompleted(false);
        return userChallengeRepository.save(uc);
    }

    @Transactional
    public void completeDay(UUID userId, UUID challengeId, int day, String note, String mood) {
        User user = userService.findById(userId);
        Challenge challenge = challengeService.getChallengeById(challengeId);
        UserChallenge uc = userChallengeRepository.findByUserAndChallenge(user, challenge)
                .orElseThrow(() -> new RuntimeException("Challenge not started"));

        if (day != uc.getCurrentDay() + 1) {
            throw new RuntimeException("Can only complete next day");
        }

        // Сохраняем прогресс дня
        DayProgress dp = new DayProgress();
        dp.setUserChallenge(uc);
        dp.setDayNumber(day);
        dp.setCompleted(true);
        dp.setNote(note);
        dp.setMood(mood);
        dp.setCompletedAt(LocalDate.now());
        dayProgressRepository.save(dp);

        // Обновляем текущий день в UserChallenge
        uc.setCurrentDay(day);
        if (day == challenge.getTotalDays()) {
            uc.setCompleted(true);
            uc.setCompletedAt(LocalDate.now().atStartOfDay());
        }
        userChallengeRepository.save(uc);
    }

    public List<UserProgressResponse> getUserProgress(UUID userId) {
        User user = userService.findById(userId);
        return userChallengeRepository.findByUser(user).stream()
                .map(uc -> {
                    UserProgressResponse resp = new UserProgressResponse();
                    resp.setChallengeId(uc.getChallenge().getId());
                    resp.setChallengeTitle(uc.getChallenge().getTitle());
                    resp.setCurrentDay(uc.getCurrentDay());
                    resp.setTotalDays(uc.getChallenge().getTotalDays());
                    resp.setCompleted(uc.isCompleted());
                    return resp;
                })
                .collect(Collectors.toList());
    }
}