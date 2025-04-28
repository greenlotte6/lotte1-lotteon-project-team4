package kr.co.lotteon.controller;

import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.repository.QnaRepository;
import kr.co.lotteon.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CsQnaController {

    private final QnaService qnaService;
    private final QnaRepository qnaRepository;

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

    @PostMapping("/qna/write")
    public String writeQna(@ModelAttribute QnaDTO qnaDTO, Model model) {
        Qna qna = Qna.builder()
                .userUid(qnaDTO.getUserUid())
                .qnaType1(qnaDTO.getQnaType1())
                .qnaType2(qnaDTO.getQnaType2())
                .title(qnaDTO.getTitle())
                .content(qnaDTO.getContent())
                .status("검토중")  // 상태는 기본적으로 '검토중'으로 설정
                .build();

        qnaService.saveQna(qna);  // QnaService에서 저장 처리

        return "redirect:/qna/coupun-list";  // 문의 등록 후 목록 페이지로 리다이렉트
    }

}


