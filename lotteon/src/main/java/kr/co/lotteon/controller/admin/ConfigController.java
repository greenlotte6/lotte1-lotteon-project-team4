package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.entity.CompanyInfo;
import kr.co.lotteon.entity.SiteInfo;
import kr.co.lotteon.entity.Terms;
import kr.co.lotteon.entity.Version;
import kr.co.lotteon.repository.TermsRepository;
import kr.co.lotteon.service.admin.CompanyInfoService;
import kr.co.lotteon.service.admin.ConfigService;
import kr.co.lotteon.service.admin.SiteInfoService;
import kr.co.lotteon.service.admin.VersionService;
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
@RequestMapping("/admin/config")
public class ConfigController {

    private final ConfigService configService;
    private final VersionService versionService;
    private final SiteInfoService siteInfoService;
    private final CompanyInfoService companyInfoService;


    @GetMapping("/banner")
    public String banner() {
        return "/admin/config/banner";
    }

    @GetMapping("/basic")
    public String basic(Model model) {
        SiteInfo siteInfo = siteInfoService.getInfo(1);
        CompanyInfo companyInfo = companyInfoService.getInfo(1);

        model.addAttribute("siteConfig", siteInfo);
        model.addAttribute("companyConfig", companyInfo);

        return "/admin/config/basic";
    }

    @PostMapping("/company")
    @ResponseBody
    public String updateCompany(@ModelAttribute SiteInfo siteInfo) {
        siteInfo.setId(1);
        siteInfoService.updateInfo(siteInfo);
        return "success";
    }

    @PostMapping("/site")
    @ResponseBody
    public String updateSiteInfo(@RequestBody SiteInfo siteInfo) {
        siteInfoService.updateInfo(siteInfo);
        return "success";
    }


    @GetMapping("/category")
    public String category() {
        return "/admin/config/category";
    }

    @GetMapping("/policy")
    public String policy(Model model) {

        TermsDTO termsDTO = configService.findById(1);
        model.addAttribute("terms", termsDTO);

        return "/admin/config/policy";
    }

    @PostMapping("/policy")
    public String policy(@ModelAttribute TermsDTO termsDTO) {

        configService.policy(termsDTO);

        return "redirect:/admin/config/policy";
    }

    @GetMapping("/version")
    public String version(Model model) {
        List<Version> versions = versionService.getAllVersions();
        model.addAttribute("versions", versions);
        return "/admin/config/version";
    }

    @PostMapping("/version/delete")
    @ResponseBody
    public String deleteVersions(@RequestBody List<Integer> selectedIds) {
        versionService.deleteVersions(selectedIds);
        return "success";
    }

}
