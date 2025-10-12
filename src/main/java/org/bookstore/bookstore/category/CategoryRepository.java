package org.bookstore.bookstore.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 카테고리 조회/저장을 위한 Repository.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByLevelOrderByName(CategoryLevel level);

    List<Category> findByParentIdOrderByName(Long parentId);

    List<Category> findByParentIdIn(List<Long> parentIds);
}
