package kr.co.lotteon.controller.Advice;

import kr.co.lotteon.dto.CategoryDTO;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.service.admin.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class LayoutControllerAdvice {

    private final SiteInfoService siteInfoService;
    private final CopyrightService copyrightService;
    private final SupportService supportService;
    private final LogoService logoService;
    private final CategoryService categoryService;

    @ModelAttribute("siteConfig")
    public SiteInfo appInfo() {
        return siteInfoService.getInfo(1);
    }

    @ModelAttribute("copyright")
    public Copyright copyrightInfo() {
        return copyrightService.getInfo(1);
    }

    @ModelAttribute("support")
    public Support SupporyInfo() {
        return supportService.getInfo(1);
    }

    @ModelAttribute("logo")
    public Logo logoInfo() {
        return logoService.getInfo(1); // 로고 추가
    }

    @ModelAttribute("categories")
    public List<CategoryDTO> categoryList() {
        return categoryService.getHierarchicalCategories();
    }


}
