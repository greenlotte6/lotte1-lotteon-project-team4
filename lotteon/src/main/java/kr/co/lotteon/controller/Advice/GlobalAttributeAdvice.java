package kr.co.lotteon.controller.Advice;

import kr.co.lotteon.entity.CompanyInfo;
import kr.co.lotteon.service.admin.CompanyInfoService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice

public class GlobalAttributeAdvice {
    private final CompanyInfoService companyInfoService;

    public GlobalAttributeAdvice(CompanyInfoService companyInfoService) {
        this.companyInfoService = companyInfoService;
    }

    @ModelAttribute("companyConfig")
    public CompanyInfo globalCompanyInfo() {
        return companyInfoService.getInfo(1); // ID가 1인 기본 정보 반환
    }
}
