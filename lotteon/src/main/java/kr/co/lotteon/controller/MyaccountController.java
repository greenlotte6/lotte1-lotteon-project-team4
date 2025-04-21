package kr.co.lotteon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyaccountController {

    @GetMapping("/myaccount/home")
    public String home() {

        return "/myaccount/home";
    }

    @GetMapping("/myaccount/buy")
    public String buy() {

        return "/myaccount/buy";
    }

    @GetMapping("/myaccount/coupon")
    public String coupon() {
        return "/myaccount/coupon";

    }

    @GetMapping("/myaccount/exchange")
    public String exchange() {

        return "/myaccount/exchange";
    }

    @GetMapping("/myaccount/info")
    public String info() {

        return "/myaccount/info";
    }

    @GetMapping("/myaccount/inquiry")
    public String inquiry() {

        return "/myaccount/inquiry";
    }

    @GetMapping("/myaccount/ireview")
    public String ireview() {

        return "/myaccount/ireview";
    }

    @GetMapping("/myaccount/order")
    public String order() {

        return "/myaccount/order";
    }

    @GetMapping("/myaccount/point")
    public String point() {
        return "/myaccount/point";

    }
    @GetMapping("/myaccount/qna")
    public String qna() {
        return "/myaccount/qna";

    }
    //
    @GetMapping("/myaccount/review")
    public String review () {

        return "/myaccount/review";
    }

    @GetMapping("/myaccount/seller")
    public String seller () {

        return "/myaccount/seller";
    }

}
