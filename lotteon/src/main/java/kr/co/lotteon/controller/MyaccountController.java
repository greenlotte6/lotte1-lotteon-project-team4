package kr.co.lotteon.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.UsersRepository;
import kr.co.lotteon.service.SellerService;
import kr.co.lotteon.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
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


    private final UsersService usersService;

    @PostMapping("/myaccount/info-update")
    public String updateUserInfo(@ModelAttribute UsersDTO dto, HttpSession session) {
        Users loginUser = (Users) session.getAttribute("user");

        if (loginUser != null) {
            loginUser.setEmail(dto.getEmail());
            loginUser.setHp(dto.getHp());
            loginUser.setZip(dto.getZip());
            loginUser.setAddr1(dto.getAddr1());
            loginUser.setAddr2(dto.getAddr2());

            usersService.save(loginUser);
            session.setAttribute("user", loginUser);
        }

        return "redirect:/myaccount/info";
    }

    private final UsersRepository usersRepository;

    @GetMapping("/myaccount/delete")
    public String deleteUser(HttpSession session) {
        Users loginUser = (Users) session.getAttribute("user");
        if (loginUser != null) {
            usersRepository.deleteById(loginUser.getUid());
            session.invalidate();
        }
        return "redirect:/";
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
