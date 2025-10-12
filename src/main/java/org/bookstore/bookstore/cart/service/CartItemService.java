package org.bookstore.bookstore.cart.service;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.cart.entity.Cart;
import org.bookstore.bookstore.cart.entity.CartItem;
import org.bookstore.bookstore.cart.repository.CartItemRepository;
import org.bookstore.bookstore.products.Products;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    /**
     * 장바구니 아이템 조회
     */
    public Optional<CartItem> getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }

    /**
     * 장바구니의 특정 상품 아이템 조회
     */
    public Optional<CartItem> getCartItemByProduct(Long cartId, Long productId) {
        return cartItemRepository.findByCartIdAndProductId(cartId, productId);
    }

    /**
     * 장바구니 아이템 존재 여부
     */
    public boolean existsCartItem(Long cartId, Long productId) {
        return cartItemRepository.existsByCart_CartIdAndProduct_ProductId(cartId, productId);
    }

    /**
     * 장바구니 아이템 저장
     */
    @Transactional
    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    /**
     * 장바구니 아이템 생성 및 저장
     */
    @Transactional
    public CartItem createCartItem(Cart cart, Products product, Integer quantity) {
        CartItem cartItem = new CartItem(cart, product, quantity);
        cart.addItem(cartItem);
        return cartItemRepository.save(cartItem);
    }

    /**
     * 장바구니 아이템 수량 변경
     */
    @Transactional
    public void updateQuantity(Long cartItemId, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("장바구니 아이템을 찾을 수 없습니다."));

        // 재고 확인
        if (!cartItem.isStockAvailable(quantity)) {
            throw new IllegalArgumentException("재고가 부족합니다. (현재 재고: " + cartItem.getProduct().getStockQuantity() + "개)");
        }

        cartItem.setQuantity(quantity);
    }

    /**
     * 장바구니 아이템 삭제
     */
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("장바구니 아이템을 찾을 수 없습니다."));

        Cart cart = cartItem.getCart();
        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
    }

    /**
     * 선택한 장바구니 아이템들 삭제
     */
    @Transactional
    public void deleteCartItems(List<Long> cartItemIds) {
        if (cartItemIds == null || cartItemIds.isEmpty()) {
            return;
        }

        List<CartItem> items = cartItemRepository.findAllById(cartItemIds);
        items.forEach(item -> {
            Cart cart = item.getCart();
            cart.removeItem(item);
        });
        cartItemRepository.deleteAllById(cartItemIds);
    }

    /**
     * 장바구니 아이템 수량 증가
     */
    @Transactional
    public void addQuantity(Long cartItemId, Integer amount) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("장바구니 아이템을 찾을 수 없습니다."));

        int newQuantity = cartItem.getQuantity() + amount;

        if (!cartItem.isStockAvailable(newQuantity)) {
            throw new IllegalArgumentException("재고가 부족합니다. (현재 재고: " + cartItem.getProduct().getStockQuantity() + "개)");
        }

        cartItem.addQuantity(amount);
    }
}