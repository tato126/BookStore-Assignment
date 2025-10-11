package org.bookstore.bookstore.products.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 파일 업로드 서비스 클래스.
 * 이미지 파일을 로컬 디스크에 저장하고 접근 가능한 URL을 반환합니다.
 *
 * @author chan
 */
@Service
public class FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 이미지 파일을 업로드하고 저장된 파일의 접근 경로를 반환합니다.
     *
     * @param file 업로드할 이미지 파일
     * @return 저장된 파일의 접근 경로 (예: /uploads/products/uuid_filename.jpg)
     * @throws IOException 파일 저장 실패 시
     */
    public String uploadImage(MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        // 업로드 디렉토리가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 원본 파일명 가져오기
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("파일명이 유효하지 않습니다.");
        }

        // UUID를 사용하여 고유한 파일명 생성 (중복 방지)
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        // 파일 저장
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 웹에서 접근 가능한 URL 경로 반환
        return "/uploads/products/" + uniqueFileName;
    }

    /**
     * 이미지 파일 타입인지 검증합니다.
     *
     * @param file 검증할 파일
     * @return 이미지 파일이면 true, 아니면 false
     */
    public boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
