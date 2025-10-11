package org.bookstore.bookstore.products.repository;

import org.bookstore.bookstore.products.Products;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 상품 Repository 인터페이스.
 * {@link Products} 엔티티에 대한 데이터베이스 접근을 제공합니다.
 *
 * @author chan
 */
public interface ProductsRepository extends JpaRepository<Products, Long> {

}
