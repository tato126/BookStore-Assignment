package org.bookstore.bookstore.products.repository;

import org.bookstore.bookstore.category.Category;
import org.bookstore.bookstore.products.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 상품 Repository 인터페이스.
 * {@link Products} 엔티티에 대한 데이터베이스 접근을 제공합니다.
 *
 * @author chan
 */
public interface ProductsRepository extends JpaRepository<Products, Long> {
    Page<Products> findByCategory(Category category, Pageable pageable);

    Page<Products> findByCategoryIn(List<Category> categories, Pageable pageable);
}
