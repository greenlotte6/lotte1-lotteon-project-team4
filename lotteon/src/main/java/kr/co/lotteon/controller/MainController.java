package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.security.MyUserDetails;
import kr.co.lotteon.service.MainService;
import kr.co.lotteon.service.NoticeService;
import kr.co.lotteon.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final MainService mainService;
    private final NoticeService noticeService;
    private final QnaService qnaService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal MyUserDetails userDetails) {
        if (userDetails != null) {
            System.out.println("로그인한 사용자 ID: " + userDetails.getUsername());
        }
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