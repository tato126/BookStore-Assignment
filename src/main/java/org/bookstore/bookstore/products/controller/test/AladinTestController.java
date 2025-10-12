package org.bookstore.bookstore.products.controller.test;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.products.dto.aladin.AladinBookItem;
import org.bookstore.bookstore.products.dto.aladin.AladinSearchResponse;
import org.bookstore.bookstore.products.service.external.AladinApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 알라딘 API 테스트 컨트롤러.
 * 개발 단계에서 알라딘 API 연동을 테스트하기 위한 컨트롤러입니다.
 *
 * @author chan
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/test/aladin")
public class AladinTestController {

    private final AladinApiService aladinApiService;

    /**
     * 도서 검색 테스트.
     * 예시: http://localhost:8080/test/aladin/search?query=해리포터
     *
     * @param query 검색 키워드
     * @return 검색 결과
     */
    @GetMapping("/search")
    public AladinSearchResponse testSearch(@RequestParam String query) {
        return aladinApiService.searchBooks(query, 1);
    }

    /**
     * 베스트셀러 조회 테스트.
     * 예시: http://localhost:8080/test/aladin/bestsellers
     *
     * @return 베스트셀러 목록
     */
    @GetMapping("/bestsellers")
    public AladinSearchResponse testBestsellers() {
        return aladinApiService.getBestsellers("Bestseller");
    }

    /**
     * ISBN 조회 테스트.
     * 예시: http://localhost:8080/test/aladin/isbn?isbn=9788936434267
     *
     * @param isbn ISBN-13
     * @return 도서 정보
     */
    @GetMapping("/isbn")
    public AladinBookItem testIsbn(@RequestParam String isbn) {
        return aladinApiService.getBookByIsbn(isbn);
    }
}
