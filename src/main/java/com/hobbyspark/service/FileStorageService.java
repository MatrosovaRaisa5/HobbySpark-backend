package com.hobbyspark.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileStorageService {

    private final Path uploadPath;

    public FileStorageService(@Value("${app.uploads-dir}") String uploadDir) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать папку для загрузок", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";
        String filename = UUID.randomUUID().toString() + ext;
        try {
            Path targetPath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), targetPath);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения файла", e);
        }
    }


    public String storeBase64(String base64) {
        if (base64.contains(",")) {
            base64 = base64.split(",")[1];
        }
        byte[] bytes = Base64.getDecoder().decode(base64);
        String filename = UUID.randomUUID().toString() + ".jpg";
        try {
            Files.write(uploadPath.resolve(filename), bytes);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения файла", e);
        }
    }
}