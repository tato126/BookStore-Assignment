package org.bookstore.bookstore.products.repository;

import org.bookstore.bookstore.products.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
