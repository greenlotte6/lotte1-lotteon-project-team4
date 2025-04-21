package kr.co.lotteon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CompanyController {

    @GetMapping("/culture")
    public String culture() {
        return "/company/culture";
    }

    @GetMapping("/index")
    public String index() {
        return "/company/index";
    }

    @GetMapping("/media")
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
