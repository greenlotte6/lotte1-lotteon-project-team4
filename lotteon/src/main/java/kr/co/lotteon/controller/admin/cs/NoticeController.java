package kr.co.lotteon.controller.admin.cs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticeController {

    @GetMapping("/admin/notice/list")
    public String list() {
        return "/admin/cs/notice/list";
    }

    @GetMapping("/admin/notice/modify")
    public String modify() {
        return "/admin/cs/notice/modify";
    }

    @GetMapping("/admin/notice/view")
    public String view() {
        return "/admin/cs/notice/view";
    }

    @GetMapping("/admin/notice/write")
    public String write() {
        return "/admin/cs/notice/write";
    }

}
