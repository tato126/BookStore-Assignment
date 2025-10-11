package org.bookstore.bookstore.products.controller;

import org.bookstore.bookstore.products.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 상품 조회 컨트롤러.
 * 상품 목록 및 상세 정보를 조회하는 기능을 제공합니다.
 *
 * @author chan
 */
@Controller
public class ProductsController {
    private final ProductsRepository repository;

    @Autowired
    public ProductsController(ProductsRepository repository) {
        this.repository = repository;
    }

    /**
     * 전체 상품 목록을 조회합니다.
     *
     * @param model 상품 목록 데이터를 담을 모델
     * @return 상품 목록 뷰
     */
    @GetMapping("/products/list")
    public String list(Model model) {
        model.addAttribute("products", repository.findAll());
        return "products/list";
    }

    /**
     * 특정 상품의 상세 정보를 조회합니다.
     *
     * @param id    조회할 상품 ID
     * @param model 상품 상세 데이터를 담을 모델
     * @return 상품 상세 뷰
     */
    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", repository.findById(id).get());
        // TODO: 에러 핸들러 추가
        return "products/detail";
    }
}
