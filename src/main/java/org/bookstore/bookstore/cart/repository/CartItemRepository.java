package org.bookstore.bookstore.cart.repository;

import org.bookstore.bookstore.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 장바구니의 특정 상품 아이템 조회
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = :cartId AND ci.product.productId = :productId")
    Optional<CartItem> findByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);

    // 장바구니 아이템 존재 여부
    boolean existsByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);
}