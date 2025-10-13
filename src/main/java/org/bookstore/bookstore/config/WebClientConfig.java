package org.bookstore.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient 설정 클래스.
 * 외부 API 호출을 위한 HTTP 클라이언트를 구성합니다.
 *
 * @author chan
 */
@Configuration
public class WebClientConfig {

    /**
     * WebClient 빈을 생성합니다.
     * 알라딘 API 등 외부 API 호출에 사용됩니다.
     *
     * @return 설정된 WebClient 인스턴스
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(2 * 1024 * 1024)) // 2MB 버퍼 크기 설정
                .build();
    }
}
