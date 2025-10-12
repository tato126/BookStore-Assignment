package org.bookstore.bookstore.products.controller.create;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookstore.bookstore.category.CategoryLevel;
import org.bookstore.bookstore.category.CategoryService;
import org.bookstore.bookstore.products.dto.request.ProductCreateRequest;
import org.bookstore.bookstore.products.service.FileUploadService;
import org.bookstore.bookstore.products.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * 상품 등록 컨트롤러
 * 관리자가 상품을 등록하는 기능을 제공합니다.
 *
 * @author chan
 */
@RequiredArgsConstructor
@RequestMapping("/admin/product")
@Controller
public class ProductCreateController {

    private final ProductService productService;
    private final FileUploadService fileUploadService;
    private final CategoryService categoryService;

    /**
     * 상품 등록 페이지를 반환합니다.
     *
     * @return 상품 등록 뷰
     */
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("largeCategories", categoryService.getCategoriesByLevel(CategoryLevel.LARGE));
        return "products/register";
    }

    /**
     * 상품 등록을 처리합니다.
     * 이미지 파일이 있으면 업로드하고, 없으면 URL을 사용합니다.
     *
     * @param productCreateRequest 상품 생성 요청 DTO
     * @param imageFile           업로드할 이미지 파일 (선택)
     * @param redirectAttributes  리다이렉트 시 메시지 전달용
     * @return 상품 목록 페이지로 리다이렉트
     */
    @PostMapping("/register")
    public String registerProduct(
            @Valid ProductCreateRequest productCreateRequest,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        try {
            String imageUrl = productCreateRequest.imageUrl();

            // 이미지 파일이 업로드된 경우
            if (imageFile != null && !imageFile.isEmpty()) {
                // 이미지 파일 타입 검증
                if (!fileUploadService.isImageFile(imageFile)) {
                    redirectAttributes.addFlashAttribute("error", "이미지 파일만 업로드 가능합니다.");
                    return "redirect:/admin/product/register";
                }

                // 파일 업로드 및 URL 생성
                imageUrl = fileUploadService.uploadImage(imageFile);
            }

            // imageUrl을 포함한 새로운 요청 DTO 생성
            ProductCreateRequest requestWithImage = ProductCreateRequest.builder()
                    .productName(productCreateRequest.productName())
                    .description(productCreateRequest.description())
                    .price(productCreateRequest.price())
                    .stockQuantity(productCreateRequest.stockQuantity())
                    .imageUrl(imageUrl)
                    .bookSize(productCreateRequest.bookSize())
                    .categoryId(productCreateRequest.categoryId())
                    .build();

            // 상품 등록
            productService.registerProduct(requestWithImage);
            redirectAttributes.addFlashAttribute("success", "상품이 성공적으로 등록되었습니다.");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "이미지 업로드 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/admin/product/register";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "상품 등록 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/admin/product/register";
        }

        return "redirect:/";
    }
}
