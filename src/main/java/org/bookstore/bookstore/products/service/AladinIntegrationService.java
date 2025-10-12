package org.bookstore.bookstore.products.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookstore.bookstore.products.Products;
import org.bookstore.bookstore.products.dto.aladin.AladinBookItem;
import org.bookstore.bookstore.products.dto.request.ProductCreateRequest;
import org.bookstore.bookstore.products.repository.ProductsRepository;
import org.bookstore.bookstore.products.service.external.AladinApiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 알라딘 API 통합 서비스.
 * 알라딘 API와 상품 등록 기능을 연결하는 통합 서비스입니다.
 *
 * @author chan
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AladinIntegrationService {

    private final AladinApiService aladinApiService;
    private final ProductService productService;
    private final ProductsRepository productsRepository;

    /**
     * 알라딘 도서 정보를 ProductCreateRequest로 변환합니다.
     *
     * @param book          알라딘 도서 정보
     * @param stockQuantity 재고 수량 (수동 입력)
     * @param bookSize      책 크기 (수동 입력)
     * @return ProductCreateRequest DTO
     */
    public ProductCreateRequest convertToProductRequest(
            AladinBookItem book,
            Integer stockQuantity,
            Integer bookSize) {

        log.info("알라딘 도서를 ProductCreateRequest로 변환: isbn={}", book.isbn13());

        return book.toProductCreateRequest(stockQuantity, bookSize);
    }

    /**
     * 알라딘 도서를 상품으로 직접 등록합니다.
     *
     * @param isbn          ISBN-13
     * @param stockQuantity 재고 수량
     * @param bookSize      책 크기
     * @return 등록된 상품
     * @throws IllegalArgumentException ISBN이 이미 등록되어 있는 경우
     * @throws RuntimeException         알라딘 API 호출 실패 또는 도서를 찾을 수 없는 경우
     */
    @Transactional
    public Products importBookFromAladin(
            String isbn,
            Integer stockQuantity,
            Integer bookSize) {

        log.info("알라딘 도서 가져오기 시작: isbn={}", isbn);

        // 1. ISBN 중복 체크
        if (isIsbnExists(isbn)) {
            log.warn("이미 등록된 ISBN입니다: {}", isbn);
            throw new IllegalArgumentException("이미 등록된 도서입니다. ISBN: " + isbn);
        }

        // 2. 알라딘 API로 도서 정보 조회
        AladinBookItem book = aladinApiService.getBookByIsbn(isbn);
        if (book == null) {
            log.error("알라딘에서 도서를 찾을 수 없습니다: isbn={}", isbn);
            throw new RuntimeException("알라딘에서 도서를 찾을 수 없습니다. ISBN: " + isbn);
        }

        // 3. ProductCreateRequest로 변환
        ProductCreateRequest request = convertToProductRequest(book, stockQuantity, bookSize);

        // 4. 상품 등록
        Products product = productService.registerProduct(request);

        log.info("알라딘 도서 등록 완료: productId={}, isbn={}, title={}",
                product.getProductId(), isbn, product.getProductName());

        return product;
    }

    /**
     * ISBN이 이미 등록되어 있는지 확인합니다.
     *
     * @param isbn ISBN-13
     * @return 이미 등록되어 있으면 true, 아니면 false
     */
    public boolean isIsbnExists(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }
        return productsRepository.existsByIsbn(isbn);
    }

    /**
     * ISBN으로 등록된 상품을 조회합니다.
     *
     * @param isbn ISBN-13
     * @return 조회된 상품 (Optional)
     */
    public Products findByIsbn(String isbn) {
        return productsRepository.findByIsbn(isbn)
                .orElse(null);
    }
}
