package org.bookstore.bookstore.category;

import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.category.dto.CategoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * 카테고리 Ajax 조회용 REST 컨트롤러.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getCategories(
            @RequestParam CategoryLevel level,
            @RequestParam(required = false) Long parentId
    ) {
        List<Category> categories;
        if (level == CategoryLevel.LARGE) {
            categories = categoryService.getCategoriesByLevel(CategoryLevel.LARGE);
        } else {
            if (parentId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "parentId 파라미터가 필요합니다.");
            }
            categories = categoryService.getChildren(parentId);
        }
        return categories.stream().map(CategoryResponse::from).toList();
    }
}
