package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.entity.Terms;
import kr.co.lotteon.repository.TermsRepository;
import kr.co.lotteon.service.admin.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        List<TermsDTO> termsDTOList = configService.findAll();
        model.addAttribute("terms", termsDTOList);

        log.info("terms : " + termsDTOList);

        return "/admin/config/policy";
    }

//    @PostMapping("/admin/config/policy")
//    public String policy(Terms terms) {
//        log.info("terms : " + terms);
//        configService.modifyPolicy(terms);
//
//        return "redirect:/admin/config/policy";
//    }


    @GetMapping("/admin/config/version")
    public String version() {
        return "/admin/config/version";
    }

}
