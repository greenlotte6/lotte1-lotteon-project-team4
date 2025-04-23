package kr.co.lotteon.controller.admin.cs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QnaController {

    @GetMapping("/admin/cs/qna/list")
    public String list() {
        return "/admin/cs/qna/list";
    }

    @GetMapping("/admin/cs/qna/view")
    public String view() {
        return "/admin/cs/qna/view";
    }

    @GetMapping("/admin/cs/qna/write")
    public String write() {
        return "/admin/cs/qna/write";
    }

}
