package org.bookstore.bookstore.products.controller;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.products.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/products")
@Controller
public class RegisterProductsController {

    private final ProductsService productsService;

    // 상품 등록 화면을 불러온다.
    // 값을 입력하고 데이터를 담아 보낸다.
    @GetMapping("/register")
    public String productMain() {
        return "products/register";
    }

    @PostMapping
    public String createProducts(String productName, String description, Long price, Long quantity) {

        productsService.register(productName, description, price, quantity);

        return "redirect:products/list";
    }
}
