package kr.co.lotteon.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecruitController {

    @GetMapping("/admin/recruit/list")
    public String list() {
        return "/admin/cs/recruit/list";
    }

}
