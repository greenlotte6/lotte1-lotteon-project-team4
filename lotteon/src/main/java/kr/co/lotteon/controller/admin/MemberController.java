package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.service.UsersService;
import kr.co.lotteon.service.admin.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final UsersService usersService;
    private final MemberService memberService;

    @GetMapping("/admin/member/list")
    public String list(Model model) {

        List<UsersDTO> users = usersService.findAll();
        model.addAttribute("users", users);

        log.info("users: {}", users);

        // 회원 목록 불러오기
        return "/admin/member/list";
    }

    @GetMapping("/admin/member/point")
    public String point(Model model) {

        List<PointDTO> points = memberService.selectPoint();
        model.addAttribute("points", points);

        //log.info("point: {}", points);

        return "/admin/member/point";
    }



}
