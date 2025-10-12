package org.bookstore.bookstore.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bookstore.bookstore.category.Category;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 소분류 카테고리

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
     * @param createdAt     생성 일시
     * @param updatedAt     수정 일시
     */
    @Builder
    public Products(Long productId, String imageUrl, int bookSize, String productName, String description, int price, int stockQuantity, LocalDateTime createdAt, LocalDateTime updatedAt, Category category) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.bookSize = bookSize;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt;
        this.category = category;
    }

}
