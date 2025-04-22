package kr.co.lotteon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("ProductController")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    @GetMapping("/product/list")
    public String list() {
        return "/product/list";
    }

    @GetMapping("/product/complete")
    public String complete() {
        return "/product/complete";
    }

    @GetMapping("/product/cart")
    public String cart() {
        return "/product/cart";
    }

    @GetMapping("/product/order")
    public String order() {
        return "/product/order";
    }

    @GetMapping("/product/search")
    public String search() {
        return "/product/search";
    }

    @GetMapping("/product/view")
    public String view() {
        return "/product/view";
    }
}
