package org.bookstore.bookstore.products.controller;

import org.bookstore.bookstore.products.Products;
import org.bookstore.bookstore.products.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductsController {
    private final ProductsRepository repository;
    @Autowired
    public ProductsController(ProductsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    @ResponseBody
    String hello() {
        return "굿모닝!";
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("product", new Products());
        return "products/register";
    }


    @GetMapping("/products/list")
    public String list(Model model) {
        model.addAttribute("products", repository.findAll());
        return "products/list";
    }


    @PostMapping("/products")
    String addProduct(Products product,
                      @RequestParam("name") String name,
                      @RequestParam("description") String description,
                      @RequestParam("price") String price,
                      @RequestParam("stock") Integer stock
                    ){
        product.setProductName(name);
        product.setDescription(description);
        product.setPrice(Integer.parseInt(price));
        product.setStockQuantity(stock);

        repository.save(product); // Products 엔티티가 DB에 insert/update 된다.

        return "redirect:/products/list";
    }
}
