package org.bookstore.bookstore.cart.dto;

import org.bookstore.bookstore.cart.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long cartItemId;
    private Long productId;
    private String productName;
    private String imageUrl;
    private Integer price;
    private Integer quantity;
    private Integer stockQuantity; // 재고 수량
    private Integer totalPrice; // 해당 아이템 총 가격
    private LocalDateTime createdAt;

    // Entity -> DTO 변환
    public static CartItemDTO from(CartItem cartItem) {
        return CartItemDTO.builder().cartItemId(cartItem.getCartItemId()).productId(cartItem.getProduct().getProductId()).productName(cartItem.getProduct().getProductName()).imageUrl(cartItem.getProduct().getImageUrl()).price(cartItem.getProduct().getPrice()).quantity(cartItem.getQuantity()).stockQuantity(cartItem.getProduct().getStockQuantity()).totalPrice(cartItem.getTotalPrice()).createdAt(cartItem.getCreatedAt()).build();
    }
}
