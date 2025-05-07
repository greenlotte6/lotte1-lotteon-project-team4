package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.QnaRepository;
import kr.co.lotteon.repository.UsersRepository;
import kr.co.lotteon.service.QnaService;
import kr.co.lotteon.dto.QnaDTO;  // QnaDTO를 import 해야 함
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CsQnaController {

    private final QnaService qnaService;
    private final QnaRepository qnaRepository;
    private final UsersRepository usersRepository;

    // 쿠폰/혜택/이벤트 리스트
    @GetMapping("/qna/coupun-list")
    public String getQnaList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<QnaDTO> qnaPage = qnaService.getQnaListByType("쿠폰/혜택/이벤트", page, 10);  // 수정된 부분
        model.addAttribute("coupunList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/cs/qna/coupun-list";
    }

    // 배송 리스트
    @GetMapping("/qna/delivery-list")
    public String deliveryList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<QnaDTO> qnaPage = qnaService.getQnaListByType("배송", page, 10);  // 수정된 부분
        model.addAttribute("deliveryList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/cs/qna/delivery-list";
    }

    // 회원 리스트
    @GetMapping("/qna/member-list")
    public String memberList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<QnaDTO> qnaPage = qnaService.getQnaListByType("회원", page, 10);  // 수정된 부분
        model.addAttribute("memberList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/cs/qna/member-list";
    }

    // 주문/결제 리스트
    @GetMapping("/qna/order-list")
    public String orderList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<QnaDTO> qnaPage = qnaService.getQnaListByType("주문/결제", page, 10);  // 수정된 부분
        model.addAttribute("orderList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/cs/qna/order-list";
    }

    // 취소/반품/교환 리스트
    @GetMapping("/qna/return-list")
    public String returnList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<QnaDTO> qnaPage = qnaService.getQnaListByType("취소/반품/교환", page, 10);  // 수정된 부분
        model.addAttribute("returnList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/cs/qna/return-list";
    }

    // 여행/숙박/항공 리스트
    @GetMapping("/qna/trip-list")
    public String tripList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<QnaDTO> qnaPage = qnaService.getQnaListByType("여행/숙박/항공", page, 10);  // 수정된 부분
        model.addAttribute("tripList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/cs/qna/trip-list";
    }

    // 안전거래 리스트
    @GetMapping("/qna/safy-list")
    public String safyList(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<QnaDTO> qnaPage = qnaService.getQnaListByType("안전거래", page, 10);  // 수정된 부분
        model.addAttribute("safyList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/cs/qna/safy-list";
    }

    // QnA 보기
    @GetMapping("/qna/view/{qnaId}")
    public String viewQna(@PathVariable long qnaId, Model model) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("QnA를 찾을 수 없습니다."));
        model.addAttribute("qna", qna);
        return "/cs/qna/view";  // 상세 페이지를 위한 뷰
    }

    // QnA 작성 폼
    @GetMapping("/qna/write")
    public String write(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/member/login?redirect=/qna/write";
        }
        return "/cs/qna/write";
    }

    // QnA 작성 처리
    @PostMapping("/qna/write")
    public String writeQna(@ModelAttribute Qna qna, Principal principal) {
        String username = principal.getName();
        Users user = usersRepository.findByUid(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        qna.setUser(user);
        qnaService.createQna(
                qna.getUser(),
                qna.getQnaType1(),
                qna.getQnaType2(),
                qna.getTitle(),
                qna.getContent(),
                qna.getWriter(),
                LocalDateTime.now()
        );

        return redirectToQnaListPage(qna.getQnaType1());
    }

    // QnA 유형별 리스트 페이지로 리다이렉트
    private String redirectToQnaListPage(String qnaType1) {
        return switch (qnaType1) {
            case "쿠폰/혜택/이벤트" -> "redirect:/qna/coupun-list";
            case "배송" -> "redirect:/qna/delivery-list";
            case "회원" -> "redirect:/qna/member-list";
            case "주문/결제" -> "redirect:/qna/order-list";
            case "취소/반품/교환" -> "redirect:/qna/return-list";
            case "안전거래" -> "redirect:/qna/safy-list";
            case "여행/숙박/항공" -> "redirect:/qna/trip-list";
            default -> "redirect:/qna/coupun-list";
        };
    }
}
