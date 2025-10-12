package org.bookstore.bookstore.cart.repository;

import org.bookstore.bookstore.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // 사용자의 장바구니 조회 (장바구니 아이템들과 함께)
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems ci LEFT JOIN FETCH ci.product WHERE c.user.userId = :userId")
    Optional<Cart> findByUserIdWithItems(@Param("userId") Long userId);

    // 사용자의 장바구니 조회
    Optional<Cart> findByUser_UserId(Long userId);

    // 사용자의 장바구니 존재 여부
    boolean existsByUser_UserId(Long userId);
}