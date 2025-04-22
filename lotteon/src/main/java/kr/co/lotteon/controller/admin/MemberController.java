package kr.co.lotteon.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/admin/member/list")
    public String list() {
        return "/admin/member/list";
    }

    @GetMapping("/admin/member/point")
    public String point() {
        return "/admin/member/point";
    }



}
