package kr.co.lotteon.controller;

import kr.co.lotteon.dto.ReviewDTO;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.BannerRepository;
import kr.co.lotteon.service.ReviewService;
import kr.co.lotteon.service.admin.CouponIssuedService;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.service.QnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.repository.UsersRepository;
import kr.co.lotteon.service.SellerService;
import kr.co.lotteon.service.UsersService;
import kr.co.lotteon.service.admin.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;

import java.util.List;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyaccountController {


    private final JavaMailSenderImpl mailSender;
    private final UsersService usersService;
    private final UsersRepository usersRepository;
    private final CouponIssuedService couponIssuedService;
    private final ReviewService reviewService;
    private final BannerRepository bannerRepository;


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
    public String couponList(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String uid = userDetails.getUsername(); // UserDetails의 기본 메서드

        String couponName = "쿠폰명";
        String benefit = "할인금액";
        String couponType = "적용기준";
        String status = "상태";
        String usedDate ="유효기간";

        List<CouponIssued> CouponIssued = couponIssuedService.getIssuedCouponsByUid(uid);
        model.addAttribute("CouponIssued", CouponIssued);
        model.addAttribute("couponName", couponName);
        model.addAttribute("benefit", benefit);
        model.addAttribute("couponType", couponType);
        model.addAttribute("status", status);
        model.addAttribute("usedDate", usedDate);
        return "/myaccount/coupon";
    }

    @GetMapping("/coupon/issue")
    public String issue(Model model, @AuthenticationPrincipal UserDetails userDetails,
                        @RequestParam("cno") String cno){

        // 쿠폰 발급
        couponIssuedService.issueCoupon(cno , userDetails);

        return "redirect:/";
    }



    @GetMapping("/myaccount/exchange")
    public String exchange() {

        return "/myaccount/exchange";
    }

    @GetMapping("/myaccount/info")
    public String info(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Banner> member1Banners = bannerRepository.findByPositionAndActive("MY1", "활성");
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
        model.addAttribute("my1Banners", member1Banners);

        return "/myaccount/info";
    }


    @PostMapping("/myaccount/info-update")
    public String updateUserInfo(@ModelAttribute UsersDTO dto,
                                 @AuthenticationPrincipal UserDetails userDetails
                                 ) {

        if (userDetails != null) {
            log.info("폼으로 전달된 이메일: {}", dto.getEmail());
            log.info("폼으로 전달된전화번호: {}", dto.getHp());
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
            helper.setSubject("롯데온 이메일 인증 코드입니다!.");
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
    public String deleteUser(@AuthenticationPrincipal UserDetails userDetails,
                             HttpSession session) {

        if (userDetails != null) {
            usersService.deactivateUser(userDetails.getUsername());
            session.invalidate();
        }
        return "redirect:/";
    }

    @PostMapping("/myaccount/changePassword")
    @ResponseBody
    public String changePassword(@RequestBody Map<String, String> request,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        String uid = userDetails.getUsername();
        String currentPassword = request.get("currentPassword");
        String newPassword = request.get("newPassword");

        boolean changed = usersService.updatePasswordIfMatch(uid, currentPassword, newPassword);

        return changed ? "success" : "invalid";
    }


    private final QnaService qnaService;

    @GetMapping("/myaccount/inquiry")
    public String inquiry(HttpSession session, Model model) {
        Users loginUser = (Users) session.getAttribute("user");

        if (loginUser == null) {
            return "redirect:/member/login";
        }


        List<Qna> qnaList = qnaService.getQnaByUser(loginUser);
        model.addAttribute("qnaList", qnaList);

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
    public String qnaPage(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          Model model) {
        if (userDetails == null) return "redirect:/member/login";

        String uid = userDetails.getUsername();
        Page<Qna> qnaPage = qnaService.getQnaPageByUserUid(uid, page, size);

        model.addAttribute("qnaPage", qnaPage);
        return "/myaccount/qna";
    }




    @PostMapping("/qna/submit")
    public String submitQna(@ModelAttribute QnaDTO dto, HttpSession session) {
        Users loginUser = (Users) session.getAttribute("user");
        if (loginUser == null) return "redirect:/member/login";

        qnaService.createQna(loginUser, dto.getQnaType1(), dto.getQnaType2(),
                dto.getTitle(), dto.getContent(), dto.getWriter(), LocalDateTime.now());
        return "redirect:/myaccount/inquiry";
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
        Optional<Seller> optionalSeller = sellerService.getSellerByCompany(company);

        if (optionalSeller.isEmpty()) {
            model.addAttribute("error", "판매자 정보를 찾을 수 없습니다.");
            return "error/404"; // 혹은 return ResponseEntity.notFound().build();
        }

        model.addAttribute("seller", optionalSeller.get());
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

    @PostMapping("/myaccount/review")
    public String reviewRegister(@ModelAttribute ReviewDTO reviewDTO,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println(reviewDTO);

        //pid 고정으로 넣기
        reviewDTO.setProducts_pid(16);


        reviewDTO.setUsers_uid(userDetails.getUsername());
        reviewService.saveReview(reviewDTO, userDetails);
        return "/myaccount/ireview";
    }

    @GetMapping("/myaccount/seller")
    public String sellerPage(HttpSession session, Model model) {
        Seller seller = (Seller) session.getAttribute("seller");

        if (seller == null) {
            return "redirect:/member/login";
        }

        model.addAttribute("seller", seller);
        return "/myaccount/seller";
    }





/*
    @PostMapping("/myaccount/ireview")
    public String submitReview(@ModelAttribute ReviewDTO reviewDTO,
                               @RequestParam("files") MultipartFile[] files,
                               Principal principal) {

        // 로그인한 사용자 정보 설정
        reviewDTO.setUsers_uid(principal.getName()); // 또는 session에서 꺼내는 방식

        reviewService.saveReview(reviewDTO, files);

        return "redirect:/myaccount/review-success"; // 저장 후 이동할 페이지
    }
*/

    @GetMapping("/myaccount/return-modal")
    public String returnModal(){
        return "/myaccount/return :: modalContent";
    }





}