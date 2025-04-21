package kr.co.lotteon.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {

    @GetMapping("/admin/faq/list")
    public String list() {
        return "/admin/cs/faq/list";
    }

    @GetMapping("/admin/faq/modify")
    public String modify() {
        return "/admin/cs/faq/modify";
    }

    @GetMapping("/admin/faq/view")
    public String view() {
        return "/admin/cs/faq/view";
    }

    @GetMapping("/admin/faq/write")
    public String write() {
        return "/admin/cs/faq/write";
    }

}
