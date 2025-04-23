package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;

@Controller
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/admin/cs/notice/list")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "전체") String type,
                       Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("noticeId").descending());
        Page<Notice> noticePage;

        if (type.equals("전체")) {
            noticePage = noticeService.getNoticePage(pageable);
        } else {
            noticePage = noticeService.getNoticePageByType(type, pageable);
        }

        model.addAttribute("noticeList", noticePage.getContent());
        model.addAttribute("page", noticePage);
        model.addAttribute("type", type);  // 선택 유지용
        return "/admin/cs/notice/list";
    }




    @GetMapping("/admin/cs/notice/modify")
        public String showModifyPage(@RequestParam("id") int id, Model model) {
            Notice notice = noticeService.getNoticeById(id);
            model.addAttribute("notice", notice);
            return "/admin/cs/notice/modify";
        }

        @PostMapping("/admin/cs/notice/modify")
        public String modifyNotice(@ModelAttribute Notice notice) {
            noticeService.updateNotice(notice);
            return "redirect:/admin/cs/notice/view?id=" + notice.getNoticeId();
        }



        @GetMapping("/admin/cs/notice/view")
        public String view(@RequestParam("id") int id, Model model) {
            Notice notice = noticeService.findById(id);
            model.addAttribute("notice", notice);
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

    @GetMapping("/admin/cs/notice/delete")
    public String deleteNotice(@RequestParam("id") int id) {
        noticeService.deleteNotice(id);
        return "redirect:/admin/cs/notice/list";
    }
    @PostMapping("/admin/cs/notice/deleteSelected")
    public String deleteSelectedNotices(@RequestParam(name = "ids", required = false) List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            noticeService.deleteNoticesByIds(ids);
        }
        return "redirect:/admin/cs/notice/list";
    }


}
