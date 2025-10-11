package org.bookstore.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * Spring MVC 설정 클래스.
 * 정적 리소스 경로를 설정합니다.
 *
 * @author chan
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 업로드된 파일에 대한 정적 리소스 핸들러를 추가합니다.
     * /uploads/products/** 경로로 접근 시 실제 파일 시스템의 업로드 디렉토리를 참조합니다.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = Paths.get(uploadDir).toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/uploads/products/**")
                .addResourceLocations(absolutePath);
    }
}
