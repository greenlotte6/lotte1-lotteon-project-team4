package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.repository.NoticeRepository;
import kr.co.lotteon.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;

    public NoticeController(NoticeService noticeService, NoticeRepository noticeRepository) {
        this.noticeService = noticeService;
        this.noticeRepository = noticeRepository;
    }

    @GetMapping("/admin/cs/notice/list")
    public String listNotices(@RequestParam(value = "type", required = false) String type,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              Model model) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("noticeId").descending());
        Page<Notice> noticePage;


        if (type == null || type.isBlank() || type.equals("전체")) {
            noticePage = noticeRepository.findAll(pageable);
        } else {
            noticePage = noticeRepository.findByNoticeType(type, pageable);
        }

        model.addAttribute("noticeList", noticePage.getContent());
        model.addAttribute("page", noticePage);
        if (type != null) {
            model.addAttribute("param", Map.of("type", type));
        } else {
            model.addAttribute("param", Map.of());
        }
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

    @GetMapping("/admin/cs/notice/delete/{id}")
    public String deleteNotice(@PathVariable("id") int id) {
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

    // 마무리
}
