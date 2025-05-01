package kr.co.lotteon.controller.Advice;

import kr.co.lotteon.entity.Copyright;
import kr.co.lotteon.entity.SiteInfo;
import kr.co.lotteon.entity.Support;
import kr.co.lotteon.entity.Logo;
import kr.co.lotteon.service.admin.CopyrightService;
import kr.co.lotteon.service.admin.SiteInfoService;
import kr.co.lotteon.service.admin.SupportService;
import kr.co.lotteon.service.admin.LogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class LayoutControllerAdvice {

    private final SiteInfoService siteInfoService;
    private final CopyrightService copyrightService;
    private final SupportService supportService;
    private final LogoService logoService;

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
}
