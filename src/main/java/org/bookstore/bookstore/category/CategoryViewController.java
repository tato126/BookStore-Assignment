package org.bookstore.bookstore.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 관리자 카테고리 조회 페이지 컨트롤러.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/categories")
public class CategoryViewController {

    private final CategoryService categoryService;

    @GetMapping
    public String viewCategories(Model model) {
        model.addAttribute("categoryTree", categoryService.getCategoryHierarchy());
        return "category/manage";
    }
}
