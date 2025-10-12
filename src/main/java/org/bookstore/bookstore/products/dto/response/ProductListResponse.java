package org.bookstore.bookstore.products.dto.response;

import lombok.Builder;
import org.bookstore.bookstore.category.Category;
import org.bookstore.bookstore.products.Products;

import java.time.LocalDateTime;

/**
 * 상품 목록 조회 응답 DTO 클래스.
 * 상품 목록을 조회할 때 필요한 데이터를 담습니다.
 *
 * @param productId     상품 ID
 * @param productName   상품 이름
 * @param description   상품 설명
 * @param price         상품 가격
 * @param stockQuantity 재고 수량
 * @param imageUrl      상품 이미지 URL
 * @param bookSize      책 크기
 * @param createdAt     생성 일시
 * @author chan
 */
@Builder
public record ProductListResponse(
        Long productId,
        String productName,
        String description,
        Integer price,
        Integer stockQuantity,
        String imageUrl,
        Integer bookSize,
        LocalDateTime createdAt,
        Long largeCategoryId,
        String largeCategoryName,
        Long mediumCategoryId,
        String mediumCategoryName,
        Long smallCategoryId,
        String smallCategoryName,
        String categoryPath
) {
    /**
     * Products 엔티티를 ProductListResponse DTO로 변환합니다.
     *
     * @param products 변환할 Products 엔티티
     * @return ProductListResponse DTO
     */
    public static ProductListResponse from(Products products) {
        Category small = products.getCategory();
        Category medium = small != null ? small.getParent() : null;
        Category large = medium != null ? medium.getParent() : null;
        String categoryPath = buildCategoryPath(large, medium, small);

        return ProductListResponse.builder()
                .productId(products.getProductId())
                .productName(products.getProductName())
                .description(products.getDescription())
                .price(products.getPrice())
                .stockQuantity(products.getStockQuantity())
                .imageUrl(products.getImageUrl())
                .bookSize(products.getBookSize())
                .createdAt(products.getCreatedAt())
                .largeCategoryId(large != null ? large.getId() : null)
                .largeCategoryName(large != null ? large.getName() : null)
                .mediumCategoryId(medium != null ? medium.getId() : null)
                .mediumCategoryName(medium != null ? medium.getName() : null)
                .smallCategoryId(small != null ? small.getId() : null)
                .smallCategoryName(small != null ? small.getName() : null)
                .categoryPath(categoryPath)
                .build();
    }

    private static String buildCategoryPath(Category large, Category medium, Category small) {
        StringBuilder builder = new StringBuilder();
        if (large != null) {
            builder.append(large.getName());
        }
        if (medium != null) {
            if (builder.length() > 0) {
                builder.append(" > ");
            }
            builder.append(medium.getName());
        }
        if (small != null) {
            if (builder.length() > 0) {
                builder.append(" > ");
            }
            builder.append(small.getName());
        }
        return builder.length() == 0 ? "미분류" : builder.toString();
    }
}
