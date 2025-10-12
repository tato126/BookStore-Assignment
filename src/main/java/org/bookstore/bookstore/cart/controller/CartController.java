package org.bookstore.bookstore.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookstore.bookstore.cart.dto.CartItemDTO;
import org.bookstore.bookstore.cart.service.CartFacadeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartFacadeService cartFacadeService;

    /**
     * 장바구니 페이지
     * TODO: 로그인 기능 구현 후 실제 userId로 변경 필요
     */
    @GetMapping
    public String cartPage(Model model) {
        log.info("장바구니 페이지 요청");

        // 임시 userId (실제로는 세션이나 Spring Security에서 가져와야 함)
        Long userId = 1L;

        List<CartItemDTO> cartItems = cartFacadeService.getCartItems(userId);
        Integer totalPrice = cartFacadeService.getTotalPrice(userId);

        log.info("장바구니 아이템 수: {}, 총 금액: {}", cartItems.size(), totalPrice);

        model.addAttribute("cartList", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartCount", cartItems.size());

        return "cart/cart";
    }

    /**
     * 장바구니에 상품 추가
     * TODO: 로그인 기능 구현 후 실제 userId로 변경 필요
     */
    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam Integer qty,
            RedirectAttributes redirectAttributes) {

        log.info("장바구니 추가 요청 - productId: {}, qty: {}", productId, qty);

        try {
            // 임시 userId (실제로는 세션이나 Spring Security에서 가져와야 함)
            Long userId = 1L;

            cartFacadeService.addToCart(userId, productId, qty);
            log.info("장바구니 추가 성공 - userId: {}, productId: {}", userId, productId);
            redirectAttributes.addFlashAttribute("message", "장바구니에 상품이 추가되었습니다.");
            return "redirect:/cart";
        } catch (Exception e) {
            log.error("장바구니 추가 실패 - productId: {}, error: {}", productId, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/" + productId;
        }
    }

    /**
     * 장바구니 수량 변경
     */
    @PostMapping("/update/{cartItemId}")
    public String updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes) {

        try {
            cartFacadeService.updateQuantity(cartItemId, quantity);
            redirectAttributes.addFlashAttribute("message", "수량이 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }

    /**
     * 장바구니 아이템 삭제
     */
    @PostMapping("/remove/{cartItemId}")
    public String removeFromCart(
            @PathVariable Long cartItemId,
            RedirectAttributes redirectAttributes) {

        try {
            cartFacadeService.removeCartItem(cartItemId);
            redirectAttributes.addFlashAttribute("message", "상품이 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }

    /**
     * 선택 상품 삭제
     */
    @PostMapping("/remove-selected")
    public String removeSelected(
            @RequestParam(required = false) List<Long> cartItemIds,
            RedirectAttributes redirectAttributes) {

        if (cartItemIds == null || cartItemIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "삭제할 상품을 선택해주세요.");
            return "redirect:/cart";
        }

        cartFacadeService.removeSelectedItems(cartItemIds);
        redirectAttributes.addFlashAttribute("message", "선택한 상품이 삭제되었습니다.");

        return "redirect:/cart";
    }

    /**
     * 장바구니 전체 비우기
     * TODO: 로그인 기능 구현 후 실제 userId로 변경 필요
     */
    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        // 임시 userId (실제로는 세션이나 Spring Security에서 가져와야 함)
        Long userId = 1L;

        cartFacadeService.clearCart(userId);
        redirectAttributes.addFlashAttribute("message", "장바구니가 비워졌습니다.");

        return "redirect:/cart";
    }

    /**
     * 장바구니 개수 조회 (AJAX용)
     * TODO: 로그인 기능 구현 후 실제 userId로 변경 필요
     */
    @GetMapping("/count")
    @ResponseBody
    public int getCartCount() {
        // 임시 userId (실제로는 세션이나 Spring Security에서 가져와야 함)
        Long userId = 1L;

        return cartFacadeService.getCartItemCount(userId);
    }
}