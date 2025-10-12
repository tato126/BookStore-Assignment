package org.bookstore.bookstore.category;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.category.dto.CategoryHierarchy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 카테고리 조회 비즈니스 로직.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategoriesByLevel(CategoryLevel level) {
        return categoryRepository.findByLevelOrderByName(level);
    }

    public List<Category> getChildren(Long parentId) {
        if (parentId == null) {
            return Collections.emptyList();
        }
        return categoryRepository.findByParentIdOrderByName(parentId);
    }

    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다: " + categoryId));
    }

    /**
     * 주어진 카테고리 이하의 모든 소분류를 반환합니다.
     * 대분류/중분류 선택 시 상품 필터링에 활용합니다.
     */
    public List<Category> getSmallCategoriesUnder(Long categoryId) {
        Category category = getCategory(categoryId);

        if (category.getLevel() == CategoryLevel.SMALL) {
            return List.of(category);
        }

        if (category.getLevel() == CategoryLevel.MEDIUM) {
            return categoryRepository.findByParentIdOrderByName(category.getId());
        }

        // LARGE
        List<Category> result = new ArrayList<>();
        List<Category> mediumCategories = categoryRepository.findByParentIdOrderByName(category.getId());
        for (Category medium : mediumCategories) {
            result.addAll(categoryRepository.findByParentIdOrderByName(medium.getId()));
        }
        return result;
    }

    public List<CategoryHierarchy> getCategoryHierarchy() {
        List<Category> largeCategories = getCategoriesByLevel(CategoryLevel.LARGE);
        return largeCategories.stream()
                .map(this::toHierarchy)
                .collect(Collectors.toList());
    }

    private CategoryHierarchy toHierarchy(Category category) {
        List<CategoryHierarchy> children = getChildren(category.getId()).stream()
                .map(child -> CategoryHierarchy.of(child.getId(), child.getName(), child.getLevel(), toGrandChildren(child)))
                .collect(Collectors.toList());
        return CategoryHierarchy.of(category.getId(), category.getName(), category.getLevel(), children);
    }

    private List<CategoryHierarchy> toGrandChildren(Category category) {
        return getChildren(category.getId()).stream()
                .map(child -> CategoryHierarchy.of(child.getId(), child.getName(), child.getLevel(), List.of()))
                .collect(Collectors.toList());
    }
}
