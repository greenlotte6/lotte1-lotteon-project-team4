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
        model.addAttribute("terms", policyService.getTermsSet(1));
        return "/policy/seller";
    }

   // @GetMapping("/policy/seller/extra")
   // public String policySellerExtra(Model model) {
   //     model.addAttribute("terms", policyService.getTermsSet(3));
   //     return "/policy/seller-extra";
   // }


}
