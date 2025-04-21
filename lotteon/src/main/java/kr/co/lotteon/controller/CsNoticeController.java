package kr.co.lotteon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
public class CsNoticeController {

    @GetMapping("/notice/all-list")
    public String alllist() {
        return "/cs/notice/all-list";
    }

    @GetMapping("/notice/custom-list")
    public String customlist() {
        return "/cs/notice/custom-list";
    }

    @GetMapping("/notice/event.list")
    public String eventlist() {
        return "/cs/notice/event-list";
    }

    @GetMapping("/notice/harm-list")
    public String harmlist() {
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
