package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/admin/cs/notice/list")
    public String list(Model model) {
        List<Notice> noticeList = noticeService.getAllNotices();
        model.addAttribute("noticeList", noticeList);
        return "/admin/cs/notice/list";
    }

    @GetMapping("/admin/cs/notice/modify")
    public String modify() {
        return "/admin/cs/notice/modify";
    }

    @GetMapping("/admin/cs/notice/view")
    public String view() {
        return "/admin/cs/notice/view";
    }

    @GetMapping("/admin/cs/notice/write")
    public String write() {
        return "/admin/cs/notice/write";
    }


    @PostMapping("/admin/cs/notice/write")
    public String submitNotice(@ModelAttribute Notice notice) {
        noticeService.saveNotice(notice);
        return "redirect:/admin/cs/notice/list";
    }

}
