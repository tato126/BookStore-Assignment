package org.bookstore.bookstore.products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookstore.bookstore.products.Products;
import org.bookstore.bookstore.products.dto.aladin.AladinBookItem;
import org.bookstore.bookstore.products.dto.aladin.AladinSearchResponse;
import org.bookstore.bookstore.products.service.AladinIntegrationService;
import org.bookstore.bookstore.products.service.external.AladinApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 알라딘 도서 검색 및 상품 등록 컨트롤러.
 * 알라딘 API를 통해 도서를 검색하고 상품으로 등록하는 기능을 제공합니다.
 *
 * @author chan
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/aladin")
public class AladinController {

    private final AladinApiService aladinApiService;
    private final AladinIntegrationService aladinIntegrationService;

    /**
     * 알라딘 도서 검색 페이지.
     *
     * @return 검색 페이지 뷰
     */
    @GetMapping("/search")
    public String searchPage() {
        log.debug("알라딘 도서 검색 페이지 접근");
        return "aladin/search";
    }

    /**
     * 알라딘 도서 검색 API (AJAX).
     *
     * @param query 검색 키워드
     * @param page  페이지 번호 (기본값: 1)
     * @return 검색 결과 JSON
     */
    @GetMapping("/api/search")
    @ResponseBody
    public AladinSearchResponse searchBooks(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page) {

        log.debug("알라딘 도서 검색 API 호출: query={}, page={}", query, page);

        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        }

        return aladinApiService.searchBooks(query.trim(), page);
    }

    /**
     * 알라딘 베스트셀러 조회 API (AJAX).
     *
     * @return 베스트셀러 목록 JSON
     */
    @GetMapping("/api/bestsellers")
    @ResponseBody
    public AladinSearchResponse getBestsellers() {
        log.debug("알라딘 베스트셀러 API 호출");
        return aladinApiService.getBestsellers("Bestseller");
    }

    /**
     * 알라딘 도서 선택 후 등록 양식 페이지로 이동.
     *
     * @param isbn  선택한 도서의 ISBN
     * @param model 모델
     * @return 등록 양식 페이지
     */
    @GetMapping("/select")
    public String selectBook(@RequestParam String isbn, Model model) {
        log.debug("알라딘 도서 선택: isbn={}", isbn);

        // ISBN 중복 체크
        if (aladinIntegrationService.isIsbnExists(isbn)) {
            log.warn("이미 등록된 ISBN입니다: {}", isbn);
            model.addAttribute("error", "이미 등록된 도서입니다. ISBN: " + isbn);
            return "aladin/search";
        }

        // 알라딘 API로 도서 정보 조회
        AladinBookItem book = aladinApiService.getBookByIsbn(isbn);
        if (book == null) {
            log.error("알라딘에서 도서를 찾을 수 없습니다: isbn={}", isbn);
            model.addAttribute("error", "도서 정보를 찾을 수 없습니다.");
            return "aladin/search";
        }

        model.addAttribute("book", book);
        return "aladin/register-form";
    }

    /**
     * 알라딘 도서를 상품으로 등록.
     *
     * @param isbn          ISBN-13
     * @param stockQuantity 재고 수량
     * @param bookSize      책 크기
     * @param redirectAttributes 리다이렉트 속성
     * @return 상품 목록 페이지로 리다이렉트
     */
    @PostMapping("/import")
    public String importBook(
            @RequestParam String isbn,
            @RequestParam Integer stockQuantity,
            @RequestParam Integer bookSize,
            RedirectAttributes redirectAttributes) {

        log.debug("알라딘 도서 등록 요청: isbn={}, stockQuantity={}, bookSize={}",
                isbn, stockQuantity, bookSize);

        try {
            // 도서 정보 가져오기 및 상품 등록
            Products product = aladinIntegrationService.importBookFromAladin(
                    isbn, stockQuantity, bookSize);

            redirectAttributes.addFlashAttribute("message",
                    "상품이 성공적으로 등록되었습니다: " + product.getProductName());

            log.debug("알라딘 도서 등록 성공: productId={}", product.getProductId());

            return "redirect:/products/list";

        } catch (IllegalArgumentException e) {
            // 중복 ISBN 등 비즈니스 로직 에러
            log.warn("알라딘 도서 등록 실패 (비즈니스 에러): {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/aladin/search";

        } catch (Exception e) {
            // 시스템 에러
            log.error("알라딘 도서 등록 실패 (시스템 에러): {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error",
                    "상품 등록 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/admin/aladin/search";
        }
    }
}
