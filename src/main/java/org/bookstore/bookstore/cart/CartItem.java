package org.bookstore.bookstore.cart;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

/**
 * 장바구니(Cart)에 담긴 상품 엔티티 클래스
 *
 * @author chan
 */
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartItemId; // 식별번호

//    private Long cartId;
//    private Long userId;
//    private Long productId;

    private int quantity; // 수량
    private LocalDateTime addedAt; // 추가일자
}
