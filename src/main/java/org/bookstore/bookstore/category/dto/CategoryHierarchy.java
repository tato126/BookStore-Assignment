package org.bookstore.bookstore.category.dto;

import org.bookstore.bookstore.category.CategoryLevel;

import java.util.List;

/**
 * 카테고리 계층 구조 표현용 DTO.
 */
public record CategoryHierarchy(
        Long id,
        String name,
        CategoryLevel level,
        List<CategoryHierarchy> children
) {
    public static CategoryHierarchy of(Long id, String name, CategoryLevel level, List<CategoryHierarchy> children) {
        return new CategoryHierarchy(id, name, level, children);
    }
}
