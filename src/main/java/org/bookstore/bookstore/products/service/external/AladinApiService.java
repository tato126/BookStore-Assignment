package org.bookstore.bookstore.products.service.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookstore.bookstore.products.dto.aladin.AladinBookItem;
import org.bookstore.bookstore.products.dto.aladin.AladinSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 알라딘 API 클라이언트 서비스.
 * 알라딘 오픈 API를 호출하여 도서 정보를 조회합니다.
 *
 * @author chan
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AladinApiService {

    private final WebClient webClient;

    @Value("${aladin.api.url}")
    private String apiUrl;

    @Value("${aladin.api.key}")
    private String apiKey;

    /**
     * 키워드로 도서를 검색합니다.
     *
     * @param query 검색 키워드
     * @param page  페이지 번호 (1부터 시작)
     * @return 검색 결과
     */
    public AladinSearchResponse searchBooks(String query, int page) {
        String url = String.format(
                "%s/ItemSearch.aspx?ttbkey=%s&Query=%s&QueryType=Title&MaxResults=10&start=%d&SearchTarget=Book&output=js&Version=20131101",
                apiUrl, apiKey, query, page
        );

        log.info("알라딘 API 호출: 도서 검색 - query={}, page={}", query, page);

        try {
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(AladinSearchResponse.class)
                    .block();
        } catch (Exception e) {
            log.error("알라딘 API 호출 실패: {}", e.getMessage(), e);
            throw new RuntimeException("알라딘 API 호출에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 베스트셀러 목록을 조회합니다.
     *
     * @param queryType 베스트셀러 타입 (Bestseller, BlogBest 등)
     * @return 베스트셀러 목록
     */
    public AladinSearchResponse getBestsellers(String queryType) {
        String url = String.format(
                "%s/ItemList.aspx?ttbkey=%s&QueryType=%s&MaxResults=20&SearchTarget=Book&output=js&Version=20131101",
                apiUrl, apiKey, queryType
        );

        log.info("알라딘 API 호출: 베스트셀러 - queryType={}", queryType);

        try {
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(AladinSearchResponse.class)
                    .block();
        } catch (Exception e) {
            log.error("알라딘 베스트셀러 API 호출 실패: {}", e.getMessage(), e);
            throw new RuntimeException("알라딘 베스트셀러 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * ISBN으로 도서 상세 정보를 조회합니다.
     *
     * @param isbn ISBN-13
     * @return 도서 정보
     */
    public AladinBookItem getBookByIsbn(String isbn) {
        String url = String.format(
                "%s/ItemLookUp.aspx?ttbkey=%s&itemIdType=ISBN13&ItemId=%s&output=js&Version=20131101",
                apiUrl, apiKey, isbn
        );

        log.info("알라딘 API 호출: ISBN 조회 - isbn={}", isbn);

        try {
            AladinSearchResponse response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(AladinSearchResponse.class)
                    .block();

            if (response != null && response.item() != null && !response.item().isEmpty()) {
                return response.item().get(0);
            }

            log.warn("ISBN {}에 해당하는 도서를 찾을 수 없습니다", isbn);
            return null;
        } catch (Exception e) {
            log.error("알라딘 ISBN 조회 API 호출 실패: {}", e.getMessage(), e);
            throw new RuntimeException("알라딘 ISBN 조회에 실패했습니다: " + e.getMessage());
        }
    }
}
