package kr.co.lotteon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PolicyController {

    @GetMapping("/policy/buyer")
    public String policy() {
        return "/policy/buyer";
    }

}
