package org.bookstore.bookstore.products.repository;

import org.bookstore.bookstore.products.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 상품 Repository 인터페이스.
 * {@link Products} 엔티티에 대한 데이터베이스 접근을 제공합니다.
 *
 * @author chan
 */
public interface ProductsRepository extends JpaRepository<Products, Long> {

    /**
     * ISBN으로 상품이 존재하는지 확인합니다.
     * 알라딘 API로 도서를 가져올 때 중복 등록을 방지하기 위해 사용합니다.
     *
     * @param isbn ISBN-13
     * @return 존재하면 true, 아니면 false
     */
    boolean existsByIsbn(String isbn);

    /**
     * ISBN으로 상품을 조회합니다.
     *
     * @param isbn ISBN-13
     * @return 조회된 상품 (Optional)
     */
    Optional<Products> findByIsbn(String isbn);
}
