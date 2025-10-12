package org.bookstore.bookstore.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bookstore.bookstore.products.Products;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // 생성자
    public CartItem(Cart cart, Products product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    // 수량 증가
    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    // 총 가격 계산
    public int getTotalPrice() {
        return product.getPrice() * quantity;
    }

    // 재고 확인
    public boolean isStockAvailable() {
        return product.getStockQuantity() >= quantity;
    }

    // 재고 확인 (수량 지정)
    public boolean isStockAvailable(int requestedQuantity) {
        return product.getStockQuantity() >= requestedQuantity;
    }
}