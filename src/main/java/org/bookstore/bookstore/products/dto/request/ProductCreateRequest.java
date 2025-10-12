package org.bookstore.bookstore.products.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

/**
 * {@link org.bookstore.bookstore.products.Products} 생성 요청 DTO 클래스.
 * 관리자가 새로운 상품을 등록할 때 사용되는 요청 데이터를 담습니다.
 *
 * @param productName   상품 이름 (필수)
 * @param description   상품 설명 (필수)
 * @param price         상품 가격, 0 이상 (필수)
 * @param stockQuantity 재고 수량, 0 이상 (필수)
 * @param imageUrl      상품 이미지 URL (선택)
 * @param bookSize      책 크기, 1 이상 (필수)
 * @param isbn          ISBN-13 (선택, 알라딘 API 연동 시 자동 입력)
 * @param author        저자 (선택, 알라딘 API 연동 시 자동 입력)
 * @param publisher     출판사 (선택, 알라딘 API 연동 시 자동 입력)
 * @param publishDate   출판일 (선택, 알라딘 API 연동 시 자동 입력)
 * @param originalPrice 정가 (선택)
 * @param category      카테고리 (선택)
 * @author chan
 */
@Builder
public record ProductCreateRequest(
        @NotBlank(message = "상품 이름은 필수입니다")
        String productName,

        @NotBlank(message = "상품 설명은 필수입니다")
        String description,

        @NotNull(message = "가격은 필수입니다")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다")
        @Max(value = 1000000, message = "최대 가격은 100만원 이하여야 합니다.")
        Integer price,

        @NotNull(message = "재고 수량은 필수입니다")
        @Min(value = 0, message = "재고 수량은 0 이상이어야 합니다")
        Integer stockQuantity,

        String imageUrl,

        @NotNull(message = "책 크기는 필수입니다")
        @Min(value = 1, message = "책 크기는 1 이상이어야 합니다")
        Integer bookSize,

        // 알라딘 API 연동 필드
        String isbn,
        String author,
        String publisher,
        LocalDate publishDate,
        Integer originalPrice,
        String category,

        @NotNull(message = "카테고리는 필수입니다")
        Long categoryId
) {
}