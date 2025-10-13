package org.bookstore.bookstore.products.dto.aladin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

/**
 * 알라딘 API 검색 응답 DTO.
 * 알라딘 API의 전체 응답 구조를 담습니다.
 *
 * @param title       검색 쿼리 제목
 * @param link        API 링크
 * @param totalResults 전체 검색 결과 수
 * @param startIndex  시작 인덱스
 * @param itemsPerPage 페이지당 항목 수
 * @param item        도서 정보 리스트
 * @author chan
 */
@Builder
public record AladinSearchResponse(
        @JsonProperty("title")
        String title,

        @JsonProperty("link")
        String link,

        @JsonProperty("totalResults")
        Integer totalResults,

        @JsonProperty("startIndex")
        Integer startIndex,

        @JsonProperty("itemsPerPage")
        Integer itemsPerPage,

        @JsonProperty("item")
        List<AladinBookItem> item
) {
}
