package kr.co.lotteon.controller.Advice;

import kr.co.lotteon.entity.SiteInfo;
import kr.co.lotteon.service.admin.SiteInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class LayoutControllerAdvice {
    private final SiteInfoService siteInfoService;

    @ModelAttribute("siteConfig")
    public SiteInfo appInfo() {
        return siteInfoService.getInfo(1); // 또는 null 반환될 가능성?
    }
}
