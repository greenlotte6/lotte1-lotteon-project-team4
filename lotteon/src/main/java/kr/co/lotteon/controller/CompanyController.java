package kr.co.lotteon.controller;

import kr.co.lotteon.dto.MediaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor
public class CompanyController {


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


    @GetMapping("/company/recruit")
    public String recruit() {
        return "/company/recruit";
    }

    @GetMapping("/company/story")
    public String story() {
        return "/company/story";
    }
}
