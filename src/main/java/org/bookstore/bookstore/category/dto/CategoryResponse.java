package org.bookstore.bookstore.category.dto;

import org.bookstore.bookstore.category.Category;
import org.bookstore.bookstore.category.CategoryLevel;

/**
 * 카테고리 응답 DTO.
 */
public record CategoryResponse(
        Long id,
        String name,
        CategoryLevel level,
        Long parentId
) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getLevel(),
                category.getParent() != null ? category.getParent().getId() : null
        );
    }
}
