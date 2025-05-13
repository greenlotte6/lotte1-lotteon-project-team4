package kr.co.lotteon.controller;

import ch.qos.logback.core.model.Model;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.UsersRepository;
import kr.co.lotteon.service.SellerService;
import kr.co.lotteon.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MyaccountController {


    private final JavaMailSenderImpl mailSender;
    private final UsersService usersService;

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

    @PostMapping("/myaccount/info-update")
    public String updateUserInfo(@ModelAttribute UsersDTO dto, HttpSession session) {
        Users loginUser = (Users) session.getAttribute("user");

        if (loginUser != null) {
            // 사용자가 입력한 정보로 업데이트
            loginUser.setEmail(dto.getEmail());
            loginUser.setHp(dto.getHp());
            loginUser.setZip(dto.getZip());
            loginUser.setAddr1(dto.getAddr1());
            loginUser.setAddr2(dto.getAddr2());

            // DB에 저장
            usersService.save(loginUser); // 사용자 정보를 DB에 저장

            // 세션에 저장된 사용자 정보 갱신
            session.setAttribute("user", loginUser);
        }

        return "redirect:/myaccount/info";
    }


    @PostMapping("/auth/sendCode")
    @ResponseBody
    public String sendEmailCode(@RequestParam String email, HttpSession session) {
        // 인증 코드 생성
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);
        session.setAttribute("authCode", code); // 세션에 인증 코드 저장

        // 이메일 전송
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("롯데온 이메일 인증 코드입니다.");
            helper.setText("<p>인증 코드: <b>" + code + "</b></p>", true); // HTML 형식
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "fail";
        }

        return "sent";
    }

    private final UsersRepository usersRepository;

    @PostMapping("/auth/verifyCode")
    @ResponseBody
    public String verifyEmailCode(@RequestParam String code, HttpSession session) {
        String authCode = (String) session.getAttribute("authCode");

        if (authCode != null && authCode.equals(code)) {
            return "success";
        } else {
            return "fail";
        }
    }




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