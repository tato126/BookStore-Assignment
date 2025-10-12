package org.bookstore.bookstore.products.controller;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.products.dto.response.ProductListResponse;
import org.bookstore.bookstore.products.repository.ProductsRepository;
import org.bookstore.bookstore.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * {@link org.bookstore.bookstore.products.Products} 조회 컨트롤러.
 * 상품 목록 및 상세 정보를 조회하는 기능을 제공합니다.
 *
 * @author chan
 */
@RequiredArgsConstructor
@Controller
public class ProductsController {

    private final ProductsRepository repository;

    private final ProductService productService;

    /**
     * 전체 상품 목록을 조회합니다.
     * 기본 30개씩 최신순으로 조회합니다.
     *
     * @param pageable 페이징 정보 (기본: 30개, 최신순)
     * @param model    상품 목록 데이터를 담을 모델
     * @return 상품 목록 뷰
     */
    @GetMapping("/")
    public String list(
            @PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        Page<ProductListResponse> productList = productService.findAllProducts(pageable);

        model.addAttribute("products", productList.getContent());
        model.addAttribute("page", productList);

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
