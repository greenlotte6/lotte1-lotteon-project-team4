package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.service.admin.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @GetMapping("/admin/product/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<ProductDTO> pageResponseDTO = productService.productList(pageRequestDTO);
        model.addAttribute(pageResponseDTO);

        return "/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String register() {
        return "/admin/product/register";
    }

}
