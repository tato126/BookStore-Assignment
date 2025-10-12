package org.bookstore.bookstore.cart.service;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.cart.entity.Cart;
import org.bookstore.bookstore.cart.repository.CartRepository;
import org.bookstore.bookstore.user.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;

    /**
     * 사용자의 장바구니 조회 (아이템 포함)
     */
    public Optional<Cart> getCartWithItems(Long userId) {
        return cartRepository.findByUserIdWithItems(userId);
    }

    /**
     * 사용자의 장바구니 조회
     */
    public Optional<Cart> getCart(Long userId) {
        return cartRepository.findByUser_UserId(userId);
    }

    /**
     * 사용자의 장바구니 가져오기 (없으면 생성)
     */
    @Transactional
    public Cart getOrCreateCart(Users user) {
        return cartRepository.findByUser_UserId(user.getUserId()).orElseGet(() -> cartRepository.save(new Cart(user)));
    }

    /**
     * 장바구니 존재 여부
     */
    public boolean existsCart(Long userId) {
        return cartRepository.existsByUser_UserId(userId);
    }

    /**
     * 장바구니 전체 비우기
     */
    @Transactional
    public void clearCart(Long userId) {
        cartRepository.findByUserIdWithItems(userId).ifPresent(cart -> {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        });
    }

    /**
     * 장바구니 저장
     */
    @Transactional
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
}