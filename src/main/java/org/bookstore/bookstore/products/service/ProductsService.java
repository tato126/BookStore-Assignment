package org.bookstore.bookstore.products.service;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.products.Products;
import org.bookstore.bookstore.products.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    public List<Products> findAllProducts() {

        return productsRepository.findAll();
    }

    public Products findProductsById(Long id) {

        Optional<Products> products = productsRepository.findById(id);

        if (products.isPresent()) {
            return products.get();
        } else {
            throw new NullPointerException("Product with id " + id + " not found");
        }
    }

    public Products register(String productName, String description, Long price, Long quantity) {

        Products products = new Products();
        products.setProductName(productName);
        products.setDescription(description);
        products.setPrice(price);
        products.setStockQuantity(quantity);
        products.setCreatedAt(LocalDateTime.now());

        productsRepository.save(products);

        return products;
    }
}
