package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.entity.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "/index";
    }


    @GetMapping("/admin/index")
    public String adminIndex(HttpSession session) {
        Users user = (Users) session.getAttribute("user");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/";
        }

        return "/admin/index";
    }

    @GetMapping("/cs/index")
    public String cs() {
        return "/cs/index";
    }


}