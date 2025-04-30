package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Qna;
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
import org.springframework.data.domain.Sort;


import java.time.format.DateTimeFormatter;
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
        Pageable pageable = PageRequest.of(page, 10); // 10은 한 페이지당 보여줄 항목 수
        Page<Qna> qnaPage = qnaService.getQnaListByType("쿠폰", pageable);
        model.addAttribute("qnaList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/cs/qna/coupun-list"; // 템플릿 이름
    }


    // 배송 리스트
    @GetMapping("/qna/delivery-list")
    public String deliveryList(Model model) {
        List<Qna> qnaList = qnaService.getQnaListByType("배송");
        model.addAttribute("qnaList", qnaList);
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
    public String write(Model model) {
        // 로그인된 사용자 정보에서 userUid 가져오기
        String userUid = "kc727900";

        model.addAttribute("userUid", userUid);  // userUid를 모델에 추가하여 폼에 전달
        return "/cs/qna/write";  // 경로 변경
    }

    @PostMapping("/qna/write")
    public String writeQna(@ModelAttribute QnaDTO qnaDTO, HttpSession session) {
        // 세션에서 userUid를 가져와 강제 세팅
        String userUid = "kc727900";  // 예시: 하드코딩된 사용자 UID
        qnaDTO.setUserUid(userUid);

        // 작성된 QnA 정보 로그 출력
        log.info("작성된 QnA 정보: {}", qnaDTO);

        // QnA 저장
        qnaService.createQna(
                qnaDTO.getUserUid(),
                qnaDTO.getQnaType1(),
                qnaDTO.getQnaType2(),
                qnaDTO.getTitle(),
                qnaDTO.getContent(),
                qnaDTO.getWriter(),
                qnaDTO.getDate()
        );

        // 문의 유형에 따라 리다이렉트할 URL 맵핑
        Map<String, String> redirectMap = Map.of(
                "쿠폰/혜택/이벤트", "/qna/coupun-list",
                "회원", "/qna/member-list",
                "배송", "/qna/delivery-list",
                "취소/반품/교환", "/qna/return-list",
                "안전거래", "/qna/safy-list",
                "여행/숙박/항공", "/qna/trip-list",
                "주문/결제", "/qna/order-list"
        );

        // qnaDTO의 문의 유형에 맞는 리다이렉트 URL 가져오기
        String redirectUrl = redirectMap.get(qnaDTO.getQnaType1());

        // 리다이렉트 URL이 존재하면 해당 URL로, 없으면 기본 URL로 리다이렉트
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        } else {
            return "redirect:/qna/coupun-list";
        }
    }
}
