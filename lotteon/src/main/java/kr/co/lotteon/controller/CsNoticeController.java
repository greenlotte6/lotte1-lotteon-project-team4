package kr.co.lotteon.controller;

import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RequiredArgsConstructor
@Controller
public class CsNoticeController {

    private final NoticeService noticeService;



    @GetMapping("/notice/all-list")
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

        model.addAttribute("noticesList", noticePage.getContent());
        model.addAttribute("page", noticePage);
        model.addAttribute("type", type);  // 선택 유지용
        return "/cs/notice/all-list";
    }



    @GetMapping("/notice/custom-list")
    public String custom(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "고객서비스") String type,
                       Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("noticeId").descending());
        Page<Notice> noticePage;

        if (type.equals("고객서비스")) {
            noticePage = noticeService.getNoticePage(pageable);
        } else {
            noticePage = noticeService.getNoticePageByType(type, pageable);
        }

        model.addAttribute("customList", noticePage.getContent());
        model.addAttribute("page", noticePage);
        model.addAttribute("type", type);  // 선택 유지용{

        return "/cs/notice/custom-list";

    }

    @GetMapping("/notice/event-list")
    public String event(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(defaultValue = "이벤트당첨") String type,
                         Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("noticeId").descending());
        Page<Notice> noticePage;

        if (type.equals("이벤트당첨")) {
            noticePage = noticeService.getNoticePage(pageable);
        } else {
            noticePage = noticeService.getNoticePageByType(type, pageable);
        }

        model.addAttribute("eventList", noticePage.getContent());
        model.addAttribute("page", noticePage);
        model.addAttribute("type", type);  // 선택 유지용{


        return "/cs/notice/event-list";
    }

    @GetMapping("/notice/harm-list")
    public String harm(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "위해상품") String type,
                        Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("noticeId").descending());
        Page<Notice> noticePage;

        if (type.equals("위해상품")) {
            noticePage = noticeService.getNoticePage(pageable);
        } else {
            noticePage = noticeService.getNoticePageByType(type, pageable);
        }

        model.addAttribute("harmList", noticePage.getContent());
        model.addAttribute("page", noticePage);
        model.addAttribute("type", type);  // 선택 유지용{


        return "/cs/notice/harm-list";






    }

    @GetMapping("/notice/sayty-list")
    public String saytylist() {
        return "/cs/notice/sayty-list";
    }

    @GetMapping("/notice/view")
    public String view() {
        return "/cs/notice/view";
    }








}
