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
//    private int discountRate;
//    private String imageUrl;
//    private double rating;
//    private int salesCount;
//    private Boolean isActive;

    private String productName;
    private String description;
    private int price;
    private int stockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Products() {}

    public Products(String productName, String description, int price, int stockQuantity) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = LocalDateTime.now();
    }

    // getter
    public Long getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }
    public String getDescription() {
        return description;
    }
    public int getPrice() {
        return price;
    }
    public int getStockQuantity() {
        return stockQuantity;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
