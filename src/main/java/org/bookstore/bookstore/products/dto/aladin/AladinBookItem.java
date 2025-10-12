package org.bookstore.bookstore.products.dto.aladin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.bookstore.bookstore.products.dto.request.ProductCreateRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 알라딘 API 도서 정보 DTO.
 * 알라딘 API 응답에서 개별 도서 정보를 담습니다.
 *
 * @param title         도서 제목
 * @param author        저자
 * @param isbn13        ISBN-13
 * @param cover         표지 이미지 URL
 * @param priceSales    판매가
 * @param priceStandard 정가
 * @param publisher     출판사
 * @param pubDate       출판일 (yyyy-MM-dd)
 * @param description   도서 설명
 * @param categoryName  카테고리
 * @author chan
 */
@Builder
public record AladinBookItem(
        @JsonProperty("title")
        String title,

        @JsonProperty("author")
        String author,

        @JsonProperty("isbn13")
        String isbn13,

        @JsonProperty("cover")
        String cover,

        @JsonProperty("priceSales")
        Integer priceSales,

        @JsonProperty("priceStandard")
        Integer priceStandard,

        @JsonProperty("publisher")
        String publisher,

        @JsonProperty("pubDate")
        String pubDate,

        @JsonProperty("description")
        String description,

        @JsonProperty("categoryName")
        String categoryName
) {
    /**
     * 알라딘 API 도서 정보를 ProductCreateRequest로 변환합니다.
     *
     * @param stockQuantity 재고 수량 (수동 입력)
     * @param bookSize      책 크기 (수동 입력)
     * @return ProductCreateRequest DTO
     */
    public ProductCreateRequest toProductCreateRequest(Integer stockQuantity, Integer bookSize) {
        LocalDate publishDateParsed = null;
        if (pubDate != null && !pubDate.isEmpty()) {
            try {
                publishDateParsed = LocalDate.parse(pubDate, DateTimeFormatter.ISO_DATE);
            } catch (Exception e) {
                // 날짜 파싱 실패 시 null 유지
            }
        }

        return ProductCreateRequest.builder()
                .productName(title)
                .description(description != null ? description : "")
                .price(priceSales != null ? priceSales : 0)
                .stockQuantity(stockQuantity)
                .imageUrl(cover)
                .bookSize(bookSize)
                .isbn(isbn13)
                .author(author)
                .publisher(publisher)
                .publishDate(publishDateParsed)
                .originalPrice(priceStandard)
                .category(categoryName)
                .build();
    }
}
