package kr.co.lotteon.controller.Advice;

import kr.co.lotteon.dto.CategoryDTO;
import kr.co.lotteon.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import kr.co.lotteon.service.admin.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class LayoutControllerAdvice {

    private final SiteInfoService siteInfoService;
    private final CopyrightService copyrightService;
    private final SupportService supportService;
    private final LogoService logoService;
    private final CategoryService categoryService;
    private final BannerService bannerService;


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
        return logoService.getInfo(1);
    }

    @ModelAttribute("categories")
    public List<CategoryDTO> categoryList() {
        return categoryService.getHierarchicalCategories();
    }

    @ModelAttribute("main1Banner")
    public Banner getMain1Banner() {
        List<Banner> banners = bannerService.getBannersByPosition("MAIN1").stream()
                .filter(b -> "활성".equals(b.getActive()))
                .filter(b -> {
                    LocalDate today = LocalDate.now();
                    LocalTime now = LocalTime.now();
                    return (b.getStartDay() == null || !today.isBefore(b.getStartDay())) &&
                            (b.getCloseDay() == null || !today.isAfter(b.getCloseDay())) &&
                            (b.getStartAt() == null || !now.isBefore(b.getStartAt())) &&
                            (b.getCloseAt() == null || !now.isAfter(b.getCloseAt()));
                })
                .toList();

        if (!banners.isEmpty()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(banners.size());
            return banners.get(randomIndex);
        }

        return null;
    }



}
