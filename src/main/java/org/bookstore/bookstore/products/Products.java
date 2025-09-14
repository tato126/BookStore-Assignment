package org.bookstore.bookstore.products;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

//    private ProductCategory productCategory;

    private String productName;
    private int price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    private int discountRate;
//    private int stockQuantity;
//    private String imageUrl;
//    private double rating;
//    private int salesCount;
//    private Boolean isActive;
}
