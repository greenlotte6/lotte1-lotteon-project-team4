package kr.co.lotteon.controller;

import kr.co.lotteon.entity.Seller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Coupon;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.UsersRepository;
import kr.co.lotteon.service.SellerService;
import kr.co.lotteon.service.UsersService;
import kr.co.lotteon.service.admin.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyaccountController {


    private final JavaMailSenderImpl mailSender;
    private final UsersService usersService;
    private final UsersRepository usersRepository;


    @GetMapping("/myaccount/home")
    public String home(HttpSession session) {
        if (session.getAttribute("user") == null) {

            return "redirect:/member/login";
        }

        return "/myaccount/home";
    }

    @GetMapping("/myaccount/buy")
    public String buy() {
        return "/myaccount/buy";
    }


    @GetMapping("/myaccount/buy-modal")
    public String buyModal() {

        return "/myaccount/buy :: modalContent";
    }


    private final CouponService couponService;

    @GetMapping("/myaccount/coupon")
    public String coupon(Model model) {
        List<Coupon> coupons = couponService.getAllCoupons();
        model.addAttribute("coupons", coupons);
        return "/myaccount/coupon";
    }



    @GetMapping("/myaccount/exchange")
    public String exchange() {

        return "/myaccount/exchange";
    }

    @GetMapping("/myaccount/info")
    public String info(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();

        UsersDTO userDto = usersService.getUserInfoByUserId(userId);

        String emailId = "";
        String emailDomain = "";

        if (userDto.getEmail() != null && userDto.getEmail().contains("@")) {
            String[] emailParts = userDto.getEmail().split("@");
            emailId = emailParts[0];
            emailDomain = emailParts[1];
        }

        String phone1 = "";
        String phone2 = "";
        String phone3 = "";

        if (userDto.getHp() != null && userDto.getHp().contains("-")) {
            String[] hpParts = userDto.getHp().split("-");
            if (hpParts.length == 3) {
                phone1 = hpParts[0];
                phone2 = hpParts[1];
                phone3 = hpParts[2];
            }
        }

        model.addAttribute("user", userDto);
        model.addAttribute("emailId", emailId);
        model.addAttribute("emailDomain", emailDomain);
        model.addAttribute("phone1", phone1);
        model.addAttribute("phone2", phone2);
        model.addAttribute("phone3", phone3);

        return "/myaccount/info";
    }


    @PostMapping("/myaccount/info-update")
    public String updateUserInfo(@ModelAttribute UsersDTO dto,
                                 @AuthenticationPrincipal UserDetails userDetails
                                 ) {

        if (userDetails != null) {
            log.info("폼으로 전달된 이메일: {}", dto.getEmail());
            log.info("폼으로 전달된 전화번호: {}", dto.getHp());
            log.info("폼으로 전달된 zip: {}", dto.getZip());
            log.info("폼으로 전달된 주소1: {}", dto.getAddr1());
            log.info("폼으로 전달된 주소2: {}", dto.getAddr2());

            usersService.updateUserInfo(
                    userDetails.getUsername(),
                    dto.getEmail(),
                    dto.getHp(),
                    dto.getAddr1(),
                    dto.getAddr2(),
                    dto.getZip()
            );
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

    private final SellerService sellerService;

    @GetMapping("/myaccount/seller-modal")
    public String sellerModal(@RequestParam String company, Model model) {
        Seller seller = sellerService.getSellerByCompany(company).orElse(null);
        model.addAttribute("seller", seller);
        return "/myaccount/seller :: modalContent"; // Thymeleaf fragment
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