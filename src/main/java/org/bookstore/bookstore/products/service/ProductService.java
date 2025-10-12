package org.bookstore.bookstore.products.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.products.Products;
import org.bookstore.bookstore.products.dto.request.ProductCreateRequest;
import org.bookstore.bookstore.products.dto.response.ProductListResponse;
import org.bookstore.bookstore.products.repository.ProductsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

/**
 * 상품 관리 서비스 클래스.
 * 상품 등록, 조회, 수정, 삭제 등의 비즈니스 로직을 처리합니다.
 *
 * @author chan
 */
@Validated
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductsRepository productsRepository;

    /**
     * ID로 상품 조회 (장바구니용)
     */
    public Optional<Products> findById(Long productId) {
        return productsRepository.findById(productId);
    }

    /**
     * 새로운 상품을 등록합니다.
     * DTO를 엔티티로 변환한 후 데이터베이스에 저장합니다.
     *
     * @param productCreateRequest 상품 생성 요청 DTO
     * @return 저장된 Products 엔티티
     */
    public Products registerProduct(@Valid ProductCreateRequest productCreateRequest) {

        // 엔티티 변환
        Products products = Products.builder()
                .productName(productCreateRequest.productName())
                .description(productCreateRequest.description())
                .price(productCreateRequest.price())
                .stockQuantity(productCreateRequest.stockQuantity())
                .imageUrl(productCreateRequest.imageUrl())
                .bookSize(productCreateRequest.bookSize())
                // 알라딘 API 필드
                .isbn(productCreateRequest.isbn())
                .author(productCreateRequest.author())
                .publisher(productCreateRequest.publisher())
                .publishDate(productCreateRequest.publishDate())
                .originalPrice(productCreateRequest.originalPrice())
                .category(productCreateRequest.category())
                .build();

        // 상품 저장
        return productsRepository.save(products);
    }

    /**
     * 전체 상품 목록을 페이징하여 조회합니다.
     *
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬)
     * @return 페이징 처리된 상품 목록 응답 DTO
     */
    public Page<ProductListResponse> findAllProducts(Pageable pageable) {
        // 상품 조회
        Page<Products> productsPage = productsRepository.findAll(pageable);

        // 엔티티 → DTO 변환
        return productsPage.map(ProductListResponse::from);
    }
}
