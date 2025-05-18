package kr.co.lotteon.controller;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.entity.Category;
import kr.co.lotteon.service.CategoryService;
import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("ProductController")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/product/list")
    public String list(PageRequestDTO pageRequestDTO,
                       @RequestParam(required = false) Long cateId,
                       @RequestParam(required = false) String sortType,
                       Model model) {

        // 정렬
        if (sortType != null) {
            pageRequestDTO.setSortType(sortType);
        }

        // 상품 목록 조회
        PageResponseDTO<ProductDTO> productDTOS = productService.list(pageRequestDTO);
        model.addAttribute(productDTOS);

        // 카테고리 경로 (Breadcrumb) 조회
        if (cateId != null) {
            List<Category> categoryPath = categoryService.getCategoryPath(cateId);
            model.addAttribute("categoryPath", categoryPath);

            log.info("categoryPath: {}", categoryPath);
        }

        log.info("productDTOS: {}", productDTOS);
        return "/product/list";
    }

    @GetMapping("/product/complete")
    public String complete() {
        return "/product/complete";
    }

    @GetMapping("/product/cart")
    public String cart() {
        return "/product/cart";
    }

    @GetMapping("/product/order")
    public String order() {
        return "/product/order";
    }

    @GetMapping("/product/search")
    public String search() {
        return "/product/search";
    }

    @GetMapping("/product/view")
    public String view() {
        return "/product/view";
    }
}
