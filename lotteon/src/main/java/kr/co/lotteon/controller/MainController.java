package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String index() {
        return "/index";
    }


    @GetMapping("/admin/index")
    public String adminIndex(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/";
        }

        List<NoticeDTO> noticeDTOList = mainService.findAll();
        List<QnaDTO> qnaDTOList = mainService.findAllQna();

        model.addAttribute("noticeList", noticeDTOList);
        model.addAttribute("qnaList", qnaDTOList);

        return "/admin/index";
    }

    @GetMapping("/cs/index")
    public String cs() {
        return "/cs/index";
    }


}