package com.myspring.myproject.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final Path fileLocation;

    public FileService() {
        this.fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (IOException e) {
            throw new RuntimeException("업로드 디렉토리를 생성할 수 없습니다.", e);
        }
    }

    /**
     * 파일을 저장하고, 저장된 파일의 접근 URL을 반환합니다.
     * 저장 시 파일명은 UUID + 원본 확장자로 구성됩니다.
     */
    public String store(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        // 확장자 추출 (.png, .jpg 등)
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        }

        // UUID 기반 새 파일명 생성
        String newFileName = UUID.randomUUID().toString() + extension;
        Path targetLocation = this.fileLocation.resolve(newFileName);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            // 실제 환경에 맞춰 URL 호스트/포트 변경
            return "http://localhost/uploads/" + newFileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }
}
