package com.hobbyspark.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobbyspark.dto.ChallengeDetailResponse;
import com.hobbyspark.dto.ChallengeListResponse;
import com.hobbyspark.dto.DayCompleteRequest;
import com.hobbyspark.dto.DayTaskResponse;
import com.hobbyspark.dto.GalleryItemDto;
import com.hobbyspark.dto.MaterialDto;
import com.hobbyspark.dto.StepDto;
import com.hobbyspark.dto.TipDto;
import com.hobbyspark.dto.WeeklyProgramDayDto;
import com.hobbyspark.entity.Challenge;
import com.hobbyspark.entity.ChallengeMaterial;
import com.hobbyspark.entity.DayCompletion;
import com.hobbyspark.entity.DayTask;
import com.hobbyspark.entity.User;
import com.hobbyspark.entity.UserChallenge;
import com.hobbyspark.repository.ChallengeRepository;
import com.hobbyspark.repository.DayCompletionRepository;
import com.hobbyspark.repository.DayTaskRepository;
import com.hobbyspark.repository.UserChallengeRepository;
import com.hobbyspark.repository.UserRepository;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepo;
    private final DayTaskRepository dayTaskRepo;
    private final UserChallengeRepository userChallengeRepo;
    private final DayCompletionRepository dayCompletionRepo;
    private final UserRepository userRepo;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChallengeService(ChallengeRepository challengeRepo,
                            DayTaskRepository dayTaskRepo,
                            UserChallengeRepository userChallengeRepo,
                            DayCompletionRepository dayCompletionRepo,
                            UserRepository userRepo,
                            FileStorageService fileStorageService) {
        this.challengeRepo = challengeRepo;
        this.dayTaskRepo = dayTaskRepo;
        this.userChallengeRepo = userChallengeRepo;
        this.dayCompletionRepo = dayCompletionRepo;
        this.userRepo = userRepo;
        this.fileStorageService = fileStorageService;
    }

    public List<ChallengeListResponse> getAll() {
        return challengeRepo.findAll().stream()
                .map(c -> new ChallengeListResponse(
                        c.getId(),
                        c.getTitle(),
                        c.getCategory().getName(),
                        c.getDifficulty(),
                        c.getImage()
                ))
                .collect(Collectors.toList());
    }

    public ChallengeDetailResponse getDetail(Long id) {
        Challenge c = challengeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Челлендж не найден"));
        List<WeeklyProgramDayDto> wp = c.getWeeklyProgram().stream()
                .map(d -> new WeeklyProgramDayDto(d.getDay(), d.getTitle(), d.getDescription()))
                .collect(Collectors.toList());
        List<String> materials = c.getMaterials().stream()
                .map(ChallengeMaterial::getText)
                .collect(Collectors.toList());
        return new ChallengeDetailResponse(
                c.getId(), c.getTitle(), c.getImage(),
                c.getDescription(), c.getCategory().getName(),
                c.getDifficulty(), wp, materials
        );
    }

    @Transactional
    public void startChallenge(Long userId, Long challengeId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Challenge challenge = challengeRepo.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Челлендж не найден"));
        userChallengeRepo.findByUserIdAndCompletedFalse(userId).ifPresent(uc -> {
            throw new RuntimeException("У вас уже есть активный челлендж");
        });
        UserChallenge uc = new UserChallenge();
        uc.setUser(user);
        uc.setChallenge(challenge);
        uc.setTotalDays(challenge.getWeeklyProgram().size());
        userChallengeRepo.save(uc);
    }

    public DayTaskResponse getDayTask(Long challengeId, int day) {
        DayTask dt = dayTaskRepo.findByChallengeIdAndDay(challengeId, day)
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));
        try {
            List<MaterialDto> materials = objectMapper.readValue(
                    dt.getMaterialsJson(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, MaterialDto.class)
            );
            List<StepDto> steps = objectMapper.readValue(
                    dt.getStepsJson(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, StepDto.class)
            );
            List<TipDto> tips = objectMapper.readValue(
                    dt.getTipsJson(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, TipDto.class)
            );
            List<String> questions = objectMapper.readValue(
                    dt.getQuestionsJson(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class)
            );
            List<GalleryItemDto> gallery = dt.getGalleryJson() != null
                    ? objectMapper.readValue(
                            dt.getGalleryJson(),
                            objectMapper.getTypeFactory().constructCollectionType(List.class, GalleryItemDto.class)
                    )
                    : Collections.emptyList();
            return new DayTaskResponse(challengeId, day, dt.getTitle(), dt.getIntro(),
                    materials, steps, tips, questions, gallery);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка чтения данных задания", e);
        }
    }

    @Transactional
        public void completeDay(Long userId, Long challengeId, int day, DayCompleteRequest request) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Challenge challenge = challengeRepo.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Челлендж не найден"));
        UserChallenge uc = userChallengeRepo.findByUserIdAndCompletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("Нет активного челленджа"));
        if (uc.getCurrentDay() != day) {
                throw new RuntimeException("Неверный день");
        }

        DayCompletion completion = new DayCompletion();
        completion.setUser(user);
        completion.setChallenge(challenge);
        completion.setDayNumber(day);
        completion.setMood(request.getMood());
        completion.setNoteText(request.getNote());

        if (request.getPhotoBase64() != null && !request.getPhotoBase64().isEmpty()) {
                String filename = fileStorageService.storeBase64(request.getPhotoBase64());
                completion.setPhotoPath("/uploads/" + filename);
        }

        dayCompletionRepo.save(completion);

        uc.setCurrentDay(day + 1);
        if (uc.getCurrentDay() > uc.getTotalDays()) {
                uc.setCompleted(true);
        }
        userChallengeRepo.save(uc);
        }
}