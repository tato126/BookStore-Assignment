package org.bookstore.bookstore.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 책 상품 엔티티 클래스.
 *
 * @author chan
 */
@Getter
@NoArgsConstructor
@Entity
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId; // 상품 아이디

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageUrl; // 이미지 url (Base64 데이터 포함 가능)

    private int bookSize; // 책 사이즈

    private String productName; // 상품 이름
    private String description; // 상품 설명
    private int price; // 상품 가격
    private int stockQuantity; // 상품 수량

    // 알라딘 API 연동 필드
    @Column(unique = true)
    private String isbn; // ISBN-13 (중복 등록 방지)

    private String author; // 저자
    private String publisher; // 출판사
    private LocalDate publishDate; // 출판일

    private Integer originalPrice; // 정가 (할인율 계산용)
    private String category; // 카테고리

    @CreatedDate
    private LocalDateTime createdAt; // 상품 생성일

    @LastModifiedDate
    private LocalDateTime updatedAt; // 상품 업데이트 날짜

    /**
     * Products 엔티티 생성자.
     *
     * @param productId     상품 ID
     * @param imageUrl      이미지 URL
     * @param bookSize      책 크기
     * @param productName   상품 이름
     * @param description   상품 설명
     * @param price         상품 가격
     * @param stockQuantity 재고 수량
     * @param isbn          ISBN-13
     * @param author        저자
     * @param publisher     출판사
     * @param publishDate   출판일
     * @param originalPrice 정가
     * @param category      카테고리
     * @param createdAt     생성 일시
     * @param updatedAt     수정 일시
     */
    @Builder
    public Products(Long productId, String imageUrl, int bookSize, String productName,
                   String description, int price, int stockQuantity,
                   String isbn, String author, String publisher, LocalDate publishDate,
                   Integer originalPrice, String category,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.bookSize = bookSize;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        // 알라딘 API 필드
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.originalPrice = originalPrice;
        this.category = category;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = updatedAt;
    }

}
