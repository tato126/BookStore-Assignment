package org.bookstore.bookstore.cart.service;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.cart.dto.CartItemDTO;
import org.bookstore.bookstore.cart.entity.Cart;
import org.bookstore.bookstore.products.Products;
import org.bookstore.bookstore.user.entity.Users;
import org.bookstore.bookstore.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CartFacadeService: 여러 서비스를 조합하여 비즈니스 로직을 처리하는 파사드 서비스
 * Controller는 이 서비스만 의존합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartFacadeService {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final org.bookstore.bookstore.products.service.ProductService productService;

    /**
     * 장바구니에 상품 추가
     * - 이미 있는 상품이면 수량 증가
     * - 없는 상품이면 새로 추가
     */
    @Transactional
    public void addToCart(Long userId, Long productId, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }

        // 1. 사용자 조회 (UserService에 getUserById가 없는 경우 findById 사용)
        Users user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2. 상품 조회 (ProductService에 getProductById가 없는 경우 findById 사용)
        Products product = productService.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 3. 재고 확인
        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다. (현재 재고: " + product.getStockQuantity() + "개)");
        }

        // 4. 장바구니 조회 또는 생성
        Cart cart = cartService.getOrCreateCart(user);

        // 5. 이미 장바구니에 있는지 확인
        cartItemService.getCartItemByProduct(cart.getCartId(), productId)
                .ifPresentOrElse(
                        // 이미 있으면 수량 증가
                        cartItem -> {
                            int newQuantity = cartItem.getQuantity() + quantity;
                            if (product.getStockQuantity() < newQuantity) {
                                throw new IllegalArgumentException("재고가 부족합니다. (현재 재고: " + product.getStockQuantity() + "개)");
                            }
                            cartItem.setQuantity(newQuantity);
                            cartItemService.saveCartItem(cartItem);
                        },
                        // 없으면 새로 추가
                        () -> cartItemService.createCartItem(cart, product, quantity)
                );
    }

    /**
     * 사용자의 장바구니 목록 조회
     */
    public List<CartItemDTO> getCartItems(Long userId) {
        return cartService.getCartWithItems(userId)
                .map(cart -> cart.getCartItems().stream()
                        .map(CartItemDTO::from)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    /**
     * 장바구니 아이템 수량 변경
     */
    @Transactional
    public void updateQuantity(Long cartItemId, Integer quantity) {
        cartItemService.updateQuantity(cartItemId, quantity);
    }

    /**
     * 장바구니 아이템 삭제
     */
    @Transactional
    public void removeCartItem(Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
    }

    /**
     * 선택한 장바구니 아이템 삭제
     */
    @Transactional
    public void removeSelectedItems(List<Long> cartItemIds) {
        cartItemService.deleteCartItems(cartItemIds);
    }

    /**
     * 장바구니 전체 비우기
     */
    @Transactional
    public void clearCart(Long userId) {
        cartService.clearCart(userId);
    }

    /**
     * 장바구니 총 금액 계산
     */
    public Integer getTotalPrice(Long userId) {
        return cartService.getCartWithItems(userId)
                .map(Cart::getTotalPrice)
                .orElse(0);
    }

    /**
     * 장바구니 아이템 개수
     */
    public int getCartItemCount(Long userId) {
        return cartService.getCartWithItems(userId)
                .map(cart -> cart.getCartItems().size())
                .orElse(0);
    }

    /**
     * 장바구니 총 상품 수량
     */
    public int getTotalItemCount(Long userId) {
        return cartService.getCartWithItems(userId)
                .map(Cart::getTotalItemCount)
                .orElse(0);
    }
}