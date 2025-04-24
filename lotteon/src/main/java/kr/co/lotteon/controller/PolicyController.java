package kr.co.lotteon.controller;

import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.service.PolicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PolicyController {

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/policy/buyer")
    public String policy() {
        return "/policy/buyer";
    }

    private final PolicyService policyService;

    @GetMapping("/policy/seller")
    public String policySeller(Model model) {
        // ✅ terms_id = 2 로 지정한 seller 약관 로딩
        model.addAttribute("terms", policyService.getTermsSet(2));
        return "/policy/seller";
    }

    @GetMapping("/policy/seller/extra")
    public String policySellerExtra(Model model) {
        // ✅ terms_id = 3 의 다른 seller 약관 또는 다른 항목을 가져올 수 있음
        model.addAttribute("terms", policyService.getTermsSet(3));
        return "/policy/seller-extra";
    }


}
