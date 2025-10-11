package org.bookstore.bookstore.products.service;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.products.Products;
import org.bookstore.bookstore.products.dto.request.ProductCreateRequest;
import org.bookstore.bookstore.products.repository.ProductsRepository;
import org.springframework.stereotype.Service;

/**
 * 상품 관리 서비스 클래스.
 * 상품 등록, 조회, 수정, 삭제 등의 비즈니스 로직을 처리합니다.
 *
 * @author chan
 */
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductsRepository productsRepository;

    /**
     * 새로운 상품을 등록합니다.
     * DTO를 엔티티로 변환한 후 데이터베이스에 저장합니다.
     *
     * @param productCreateRequest 상품 생성 요청 DTO
     * @return 저장된 Products 엔티티
     */
    public Products registerProduct(ProductCreateRequest productCreateRequest) {

        // 엔티티 변환
        Products products = Products.builder()
                .productName(productCreateRequest.productName())
                .description(productCreateRequest.description())
                .price(productCreateRequest.price())
                .stockQuantity(productCreateRequest.stockQuantity())
                .imageUrl(productCreateRequest.imageUrl())
                .bookSize(productCreateRequest.bookSize())
                .build();

        // 상품 저장
        return productsRepository.save(products);
    }
}
