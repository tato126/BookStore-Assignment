package org.bookstore.bookstore.products.controller;

import org.bookstore.bookstore.products.Products;
import org.bookstore.bookstore.products.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductsController {
    private final ProductsRepository repository;
    @Autowired
    public ProductsController(ProductsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("products", repository.findAll());
        return "products/list";
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("product", new Products()); // 컨트롤러에서 뷰(템플릿)로 데이터를 전달
        return "products/register";
    }


    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", repository.findById(id).get());
        // 에러 핸들러 추가
        return "products/detail";
    }


    @PostMapping("/products")
    String addProduct(
                      @RequestParam("name") String name,
                      @RequestParam("description") String description,
                      @RequestParam("imageUrl") String imageUrl,
                      @RequestParam("price") Integer price,
                      @RequestParam("stock") Integer stock
                    ){
        Products product = new Products(name, description, imageUrl, price, stock);
        repository.save(product); // Products 엔티티가 DB에 insert/update 된다.

        return "redirect:/";
    }
}
