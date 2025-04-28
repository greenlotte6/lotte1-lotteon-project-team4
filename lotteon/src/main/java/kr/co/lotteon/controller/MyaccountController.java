package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyaccountController {
    

    @GetMapping("/myaccount/home")
    public String home(HttpSession session) {
        if (session.getAttribute("user") == null) {

            return "redirect:/member/login";
        }

        return "/myaccount/home";
    }

    @GetMapping("/myaccount/buy-modal")
    public String buyModal() {

        return "/myaccount/buy :: modalContent";
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
    
    @GetMapping("/myaccount/review-modal")
    public String reviewModal() {
        return "/myaccount/review :: modalContent";
    }


    @GetMapping("/myaccount/exchange-modal")
    public String exchangeModal() {

        return "/myaccount/exchange :: modalContent";
    }

    @GetMapping("/myaccount/seller-modal")
    public String sellerModal () {

        return "/myaccount/seller :: modalContent";
    }

    @GetMapping("/myaccount/order-details-modal")
    public String orderDetailsModal () {

        return "/myaccount/order-details :: modalContent";
    }

    @GetMapping("/myaccount/inquiry-modal")
    public String inquiryModal() {

        return "/myaccount/inquiry :: modalContent";
    }

    @GetMapping("/myaccount/review")
    public String review() {
        return "/myaccount/review";
    }

    @GetMapping("/myaccount/seller")
    public String seller() {

        return "/myaccount/seller";
    }

    @GetMapping("/myaccount/return-modal")
    public String returnModal(){
        return "/myaccount/return :: modalContent";
    }



}
