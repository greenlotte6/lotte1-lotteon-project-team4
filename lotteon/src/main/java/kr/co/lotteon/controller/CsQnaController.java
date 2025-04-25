package kr.co.lotteon.controller;

import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CsQnaController {

    private final QnaService qnaService;

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
    public String submitQna(@RequestParam("primary") String primary,
                            @RequestParam("secondary") String secondary,
                            @RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("uid") String uid) {

        QnaDTO qnaDTO = QnaDTO.builder()
                .userUid(uid)
                .qnaType1(primary)
                .qnaType2(secondary)
                .title(title)
                .content(content)
                .status("대기")  // 기본 상태는 대기
                .build();

        // 서비스에서 처리
        qnaService.saveQna(qnaDTO);

        // 문의 목록 페이지로 리디렉션 (해당 카테고리의 목록 페이지로)
        return "redirect:/qna/" + primary.toLowerCase() + "-list";
    }
}


