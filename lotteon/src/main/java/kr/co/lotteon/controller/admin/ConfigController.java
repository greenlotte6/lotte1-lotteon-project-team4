package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.LogoDTO;
import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.security.MyUserDetails;
import kr.co.lotteon.service.admin.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
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
    private final CopyrightService copyrightService;
    private final SupportService supportService;
    private final LogoService logoService;


    @GetMapping("/banner")
    public String banner() {
        return "/admin/config/banner";
    }

    @GetMapping("/basic")
    public String basic(Model model) {
        SiteInfo siteInfo = siteInfoService.getInfo(1);
        CompanyInfo companyInfo = companyInfoService.getInfo(1);
        Copyright copyright = copyrightService.getInfo(1);
        Support support = supportService.getInfo(1);
        Logo logo = logoService.getInfo(1);

        model.addAttribute("siteConfig", siteInfo);
        model.addAttribute("companyConfig", companyInfo);
        model.addAttribute("copyright", copyright);
        model.addAttribute("support", support);
        model.addAttribute("logo", logo);

        return "/admin/config/basic";
    }

    @PostMapping("/company")
    @ResponseBody
    public String updateCompany(@RequestBody CompanyInfo companyInfo) {
        companyInfoService.updateInfo(companyInfo);
        return "success";
    }

    @PostMapping("/site")
    @ResponseBody
    public String updateSiteInfo(@RequestBody SiteInfo siteInfo) {
        siteInfoService.updateInfo(siteInfo);
        return "success";
    }
    @PostMapping("/copyright")
    @ResponseBody
    public String updatecopyright(@RequestBody Copyright copyright) {
        copyrightService.updateInfo(copyright);
        return "success";
    }
    @PostMapping("/support")
    @ResponseBody
    public String updatesupport(@RequestBody Support support) {
        supportService.updateInfo(support);
        return "success";
    }

    @PostMapping("/imageregister")
    @ResponseBody
    public String registerImage(@ModelAttribute LogoDTO dto) {
        try {
            logoService.registerLogo(dto);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "파일 업로드 중 오류 발생";
        }
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

    @PostMapping("/version")
    @ResponseBody
    public String saveVersion(@RequestBody Version version,
                              @AuthenticationPrincipal MyUserDetails userDetails) {
        if (userDetails == null) {
            return "unauthorized";
        }

        version.setUser(userDetails.getUsers());
        version.setRdate(LocalDateTime.now());
        versionService.save(version);

        return "success";
    }

}
