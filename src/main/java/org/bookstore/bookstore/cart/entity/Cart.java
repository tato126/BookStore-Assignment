package org.bookstore.bookstore.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bookstore.bookstore.user.entity.Users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

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
    public Cart(Users user) {
        this.user = user;
    }

    // 장바구니 아이템 추가
    public void addItem(CartItem item) {
        cartItems.add(item);
        item.setCart(this);
    }

    // 장바구니 아이템 제거
    public void removeItem(CartItem item) {
        cartItems.remove(item);
        item.setCart(null);
    }

    // 총 금액 계산
    public int getTotalPrice() {
        return cartItems.stream().mapToInt(CartItem::getTotalPrice).sum();
    }

    // 총 아이템 개수
    public int getTotalItemCount() {
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }
}