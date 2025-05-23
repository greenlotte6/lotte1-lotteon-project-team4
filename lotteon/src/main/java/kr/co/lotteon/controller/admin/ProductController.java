package kr.co.lotteon.controller.admin;

import jakarta.persistence.EntityNotFoundException;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Category;
import kr.co.lotteon.service.admin.CategoryService;
import kr.co.lotteon.service.admin.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    // 상품 목록 조회
    @GetMapping("/admin/product/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<ProductDTO> pageResponseDTO = productService.productList(pageRequestDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/admin/product/list";
    }

    // 상품 목록 검색
    @GetMapping("/admin/product/searchList")
    private String searchList(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<ProductDTO> pageResponseDTO = productService.searchList(pageRequestDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        log.info("page : {}", pageResponseDTO);

        return "/admin/product/list";
    }

    // 상품 선택 삭제
    @PostMapping("/admin/product/deleteProduct")
    private String deleteProduct(@RequestParam("pid") List<Integer> pid) {

        if(pid == null || pid.isEmpty()) {
            return "redirect:/admin/product/list?error=noProductsSelected";
        }
        productService.deleteProduct(pid);

        return "redirect:/admin/product/list";
    }

    // 상품 등록
    @GetMapping("/admin/product/register")
    public String register(Model model) {
        // 1차 카테고리 목록을 조회
        List<CategoryDTO> categories1 = categoryService.getCategories1();

        log.info("categories : {}", categories1);

        model.addAttribute("categories1", categories1);
        return "/admin/product/register";
    }

    @PostMapping("/admin/product/register")
    public String registerProduct(@ModelAttribute ProductFormDTO form) throws IOException {
        productService.registerProduct(form);
        return "redirect:/admin/product/list"; // 등록 후 상품 목록 페이지로 리다이렉트
    }

    @GetMapping("/admin/product/categories")
    @ResponseBody
    public List<CategoryDTO> getCategories2(@RequestParam("parent") Long parentId) {
        // 1차 카테고리 ID를 받아서 해당 카테고리의 2차 카테고리 목록을 조회
        Category parentCategory = Category.builder().cateId(parentId).build();
        List<CategoryDTO> categories2 = categoryService.getCategories2(parentCategory);

        log.info("parentCategory : {}", parentCategory);
        log.info("categories2 : {}", categories2);

        return categories2; // JSON 형태 반환
    }

    // 상품 수정 폼 조회
    @GetMapping("/admin/product/modifyView/{pid}")
    public String modifyView(@PathVariable int pid, Model model) {
        ProductFormDTO productFormDTO = productService.modifyView(pid);

        List<CategoryDTO> categories1 = categoryService.getCategories1();
        model.addAttribute("categories1", categories1);

        model.addAttribute("productFormDTO", productFormDTO);
        log.info("list : {}", productFormDTO);

        return "/admin/product/modify";
    }

    // 상품 수정
    @PostMapping("/admin/product/modifyView/{pid}")
    public String modifyProduct(@PathVariable int pid,
                                @ModelAttribute ProductFormDTO productFormDTO) {
        productService.modifyProduct(pid, productFormDTO);
        return "redirect:/admin/product/list";
    }


}
