package org.bookstore.bookstore.products.controller;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.products.Products;
import org.bookstore.bookstore.products.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/products")
@Controller
public class FindProductsController {

    private final ProductsService productsService;

    // 등록된 상품을 조회한다.
    @GetMapping("list")
    public String findAllProducts(Model model) {

        List<Products> products = productsService.findAllProducts();
        model.addAttribute("products", products);

        return "products/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {

        Products product = productsService.findProductsById(id);
        model.addAttribute("product", product);

        return "products/detailProduct";
    }
}
