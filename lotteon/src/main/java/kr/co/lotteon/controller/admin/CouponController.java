package kr.co.lotteon.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CouponController {

    @GetMapping("/admin/coupon/list")
    public String list() {
        return "/admin/coupon/list";
    }

    @GetMapping("/admin/coupon/issued")
    public String issued() {
        return "/admin/coupon/issued";
    }

}
