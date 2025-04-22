package kr.co.lotteon.controller;

import kr.co.lotteon.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
@RequiredArgsConstructor
public class CompanyController {

    private final VideoService videoService;

    @GetMapping("/company/culture")
    public String culture() {
        return "/company/culture";
    }

    @GetMapping("/company/index")
    public String index() {
        return "/company/index";
    }

    @GetMapping("/company/media")
    public String media() {
        return "/company/media";
    }

    @GetMapping("/recruit")
    public String recruit() {
        return "/company/recruit";
    }

    @GetMapping("/story")
    public String story() {
        return "/company/story";
    }
}
