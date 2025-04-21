package kr.co.lotteon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QnaController {

    @GetMapping("/qna/coupun-list")
    public String coupunList() {
        return "/cs/qna/coupun-list";
    }

    @GetMapping("/qna/delivery-list")
    public String deliveryList() {
        return "/cs/qna/delivery-list";
    }

    @GetMapping("/qna/member-list")
    public String memberList() {
        return "/cs/qna/member-list";
    }

    @GetMapping("/qna/order-list")
    public String orderList() {
        return "/cs/qna/order-list";
    }

    @GetMapping("/qna/return-list")
    public String returnList() {
        return "/cs/qna/return-list";
    }

    @GetMapping("/qna/safy-list")
    public String safyList() {
        return "/cs/qna/safy-list";
    }

    @GetMapping("/qna/trip-list")
    public String tripList() {
        return "/cs/qna/trip-list";
    }

    @GetMapping("/qna/view")
    public String view() {
        return "/cs/qna/view";
    }

    @GetMapping("/qna/write")
    public String write() {
        return "/cs/qna/write";
    }


}
