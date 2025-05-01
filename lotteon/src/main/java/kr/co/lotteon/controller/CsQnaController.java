package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.QnaRepository;
import kr.co.lotteon.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CsQnaController {

    private final QnaService qnaService;
    private final QnaRepository qnaRepository;

    // 쿠폰/혜택/이벤트 리스트
    @GetMapping("/qna/coupun-list")
    public String getQnaList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 10);

        System.out.println(page);
        System.out.println(page);
        System.out.println(page);
        System.out.println(page);

        Page<Qna> qnaPage = qnaService.getQnaListByType("쿠폰/혜택/이벤트", pageable);
        model.addAttribute("coupunList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/cs/qna/coupun-list";
    }


    // 배송 리스트
    @GetMapping("/qna/delivery-list")
    public String deliveryList(@RequestParam(value = "page" , defaultValue = "0") int page , Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Qna> qnaPage = qnaService.getQnaListByType("배송" , pageable);

        model.addAttribute("deliveryList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/cs/qna/delivery-list";  // 경로 변경
    }

    // 회원 리스트
    @GetMapping("/qna/member-list")
    public String memberList(Model model) {
        List<Qna> qnaList = qnaService.getQnaListByType("회원");
        model.addAttribute("qnaList", qnaList);
        return "/cs/qna/member-list";  // 경로 변경
    }

    // 주문/결제 리스트
    @GetMapping("/qna/order-list")
    public String orderList(Model model) {
        List<Qna> qnaList = qnaService.getQnaListByType("주문/결제");
        model.addAttribute("qnaList", qnaList);
        return "/cs/qna/order-list";  // 경로 변경
    }

    // 취소/반품/교환 리스트
    @GetMapping("/qna/return-list")
    public String returnList(Model model) {
        List<Qna> qnaList = qnaService.getQnaListByType("취소/반품/교환");
        model.addAttribute("qnaList", qnaList);
        return "/cs/qna/return-list";  // 경로 변경
    }

    // 안전거래 리스트
    @GetMapping("/qna/safy-list")
    public String safyList(Model model) {
        List<Qna> qnaList = qnaService.getQnaListByType("안전거래");
        model.addAttribute("qnaList", qnaList);
        return "/cs/qna/safy-list";  // 경로 변경
    }

    // 여행/숙박/항공 리스트
    @GetMapping("/qna/trip-list")
    public String tripList(Model model) {
        List<Qna> qnaList = qnaService.getQnaListByType("여행/숙박/항공");
        model.addAttribute("qnaList", qnaList);
        return "/cs/qna/trip-list";  // 경로 변경
    }

    @GetMapping("/qna/view")
    public String view() {
        return "/cs/qna/view";
    }

    @GetMapping("/qna/write")
    public String write(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {


            // 로그인되지 않은 사용자는 로그인 페이지로 리다이렉트
            return "redirect:/member/login";
        }

        String uid = userDetails.getUsername(); // 로그인된 사용자 ID
        return "/cs/qna/write";
    }

    @PostMapping("/qna/write")
    public String writeQna(@ModelAttribute QnaDTO qnaDTO) {

        log.info("작성된 QnA 정보: {}", qnaDTO);

        qnaService.createQna(
                qnaDTO.getUid(),
                qnaDTO.getQnaType1(),
                qnaDTO.getQnaType2(),
                qnaDTO.getTitle(),
                qnaDTO.getContent(),
                qnaDTO.getWriter(),
                qnaDTO.getDate()
        );

        // 작성 후 해당 유형에 맞는 리스트 페이지로 리다이렉트
        return redirectToQnaListPage(qnaDTO.getQnaType1());
    }

    // QnA 유형에 따라 리다이렉션할 페이지를 반환하는 메소드
    private String redirectToQnaListPage(String qnaType1) {
        switch (qnaType1) {
            case "쿠폰/혜택/이벤트":
                return "redirect:/qna/coupun-list";
            case "배송":
                return "redirect:/qna/delivery-list";
            case "회원":
                return "redirect:/qna/member-list";
            case "주문/결제":
                return "redirect:/qna/order-list";
            case "취소/반품/교환":
                return "redirect:/qna/return-list";
            case "안전거래":
                return "redirect:/qna/safy-list";
            case "여행/숙박/항공":
                return "redirect:/qna/trip-list";
            default:
                return "redirect:/qna/coupun-list"; // 기본값
        }
    }
}