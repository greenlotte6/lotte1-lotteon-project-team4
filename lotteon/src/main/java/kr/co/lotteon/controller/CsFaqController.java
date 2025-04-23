package kr.co.lotteon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CsFaqController {

    @GetMapping("/faq/member-list")
    public String memberlist() {
        return "/cs/faq/member-list";
    }

    @GetMapping("/faq/coupon-list")
    public String couponlist() {
        return "/cs/faq/coupon-list";
    }

    @GetMapping("/faq/order-list")
    public String orderlist() {
        return "/cs/faq/order-list";
    }

    @GetMapping("/faq/delivery")
    public String delivery() {
        return "/cs/faq/delivery";
    }

    @GetMapping("/faq/return-list")
    public String retunelist() {
        return "/cs/faq/retune-list";
    }

    @GetMapping("/faq/trip-list")
    public String triplist() {
        return "/cs/faq/trip-list";
    }

    @GetMapping("/faq/safy")
    public String safy() {
        return "/cs/faq/safy";
    }

    @GetMapping("/faq/view")
    public String view() {
        return "/cs/faq/view";
    }

}
