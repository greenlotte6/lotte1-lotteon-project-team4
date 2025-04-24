package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.entity.Terms;
import kr.co.lotteon.repository.TermsRepository;
import kr.co.lotteon.service.admin.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ConfigController {

    private final ConfigService configService;

    @GetMapping("/admin/config/banner")
    public String banner() {
        return "/admin/config/banner";
    }

    @GetMapping("/admin/config/basic")
    public String basic() {
        return "/admin/config/basic";
    }

    @GetMapping("/admin/config/category")
    public String category() {
        return "/admin/config/category";
    }

    @GetMapping("/admin/config/policy")
    public String policy(Model model) {

        TermsDTO termsDTO = configService.findById(1);
        model.addAttribute("terms", termsDTO);


        /* 메인 약관
        String[] str = termsDTO.getPurchaseTerms().split("◈");

        for(int i=1; i<str.length; i++) {
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            System.out.println(str[i]);
        }

        termsDTO.setSection1(str[1]);

        System.out.println("---------------");
        System.out.println(str[1]);
*/
//        log.info("terms : " + termsDTO);

        return "/admin/config/policy";
    }

    @PostMapping("/admin/config/policy")
    public String policy(@ModelAttribute TermsDTO termsDTO) {

        configService.policy(termsDTO);

        return "redirect:/admin/config/policy";
    }

    @GetMapping("/admin/config/version")
    public String version() {
        return "/admin/config/version";
    }

}
