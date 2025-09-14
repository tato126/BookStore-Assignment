package org.bookstore.bookstore.productReviewer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ProductReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewerId;
    private Long productId;
    private Long userId;
    private Long orderItemId;
    private int rating;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
