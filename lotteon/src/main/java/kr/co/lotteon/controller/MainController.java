package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.entity.Banner;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.repository.BannerRepository;
import kr.co.lotteon.security.MyUserDetails;
import kr.co.lotteon.service.MainService;
import kr.co.lotteon.service.NoticeService;
import kr.co.lotteon.service.QnaService;
import kr.co.lotteon.service.admin.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final MainService mainService;
    private final NoticeService noticeService;
    private final QnaService qnaService;
    private final BannerService bannerService;
    private final BannerRepository bannerRepository;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        if (userDetails != null) {
            System.out.println("로그인한 사용자 ID: " + userDetails.getUsername());
        }

        List<Banner> main2Banners = bannerRepository.findByPositionAndActive("MAIN2", "활성");
        model.addAttribute("main2Banners", main2Banners);
        return "/index";
    }

    @GetMapping("/admin/index")
    public String adminIndex(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        if (userDetails == null ||
                !userDetails.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/member/login?unauthorized";
        }

        List<Notice> notices = noticeService.getRecentNotices(5);
        List<Qna> qnas = qnaService.getRecentQnas(5);

        model.addAttribute("notices", notices);
        model.addAttribute("qnas", qnas);

        return "/admin/index";
    }

//    @GetMapping("/admin/index")
//    public String adminIndex(HttpSession session, Model model) {
//        Users user = (Users) session.getAttribute("user");
//
//        if (user == null || !"ADMIN".equals(user.getRole())) {
//            return "redirect:/admin/index";
//        }
//
//        List<NoticeDTO> noticeDTOList = mainService.findAll();
//        List<QnaDTO> qnaDTOList = mainService.findAllQna();
//
//        model.addAttribute("noticeList", noticeDTOList);
//        model.addAttribute("qnaList", qnaDTOList);
//
//        return "/admin/index";
//    }

    @GetMapping("/cs/index")
    public String cs(Model model) {
        List<Notice> notices = noticeService.getRecentNotices(5);
        List<Qna> qnas = qnaService.getRecentQnas(5);

        model.addAttribute("notices", notices);
        model.addAttribute("qnas", qnas);
        return "/cs/index";
    }
}