package kr.co.lotteon.controller;

import kr.co.lotteon.dto.*;
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

        // 정렬 세팅
        if (sortType != null) {
            pageRequestDTO.setSortType(sortType);
        }


        // 카테고리 세팅
        if (cateId != null) {
            pageRequestDTO.setCateId(cateId);

            // breadcrumb용 categoryPath 전달
            List<Category> categoryPath = categoryService.getCategoryPath(cateId);
            model.addAttribute("categoryPath", categoryPath);
            model.addAttribute("cateId", cateId);
            model.addAttribute("sortType", sortType);
        }

        // 상품 목록 조회 (카테고리, 정렬 포함 처리)
        PageResponseDTO<ProductDTO> productDTOS = productService.list(pageRequestDTO);
        model.addAttribute("pageResponseDTO", productDTOS);


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
    public String view(@RequestParam("pid") int pid,
                       @RequestParam(required = false) Long cateId,
                       PageRequestDTO pageRequestDTO,
                       Model model) {
        ProductDTO productDTO = productService.view(pid);
        model.addAttribute("productDTO", productDTO);

        ProductComplianceDTO productComplianceDTO = productService.productCompliance(pid);
        model.addAttribute("productComplianceDTO", productComplianceDTO);

        PageResponseDTO<ReviewDTO> pageResponseDTO = productService.Review(pageRequestDTO, pid);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

//        CategoryDTO categoryDTO = productService.getAllCategories(cateId);
//        model.addAttribute("categoryDTO", categoryDTO);

        return "/product/view";
    }
}
