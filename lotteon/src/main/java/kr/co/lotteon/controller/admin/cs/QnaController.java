package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.service.QnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class QnaController {

    @Autowired
    private QnaService qnaService;

    // Qna 목록 조회 (페이지네이션, qnaType1 필터링)
    @GetMapping("/admin/cs/qna/list")
    public String getQnaList(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(required = false) String qnaType1,
                             Model model) {

        int pageSize = 10;
        Page<QnaDTO> qnaPage;

        // qnaType1이 있을 경우 해당 타입에 맞는 Qna 목록 조회
        if (qnaType1 != null && !qnaType1.isEmpty()) {
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            qnaPage = qnaService.getQnaListByType(qnaType1, page - 1, pageSize);  // 수정된 부분
        } else {
            // qnaType1이 없을 경우 전체 Qna 목록 조회
            qnaPage = qnaService.getQnaPage(page - 1, pageSize);
        }

        // 뷰에 전달할 데이터
        model.addAttribute("qnaList", qnaPage.getContent());
        model.addAttribute("currentPage", page);  // 현재 페이지 번호
        model.addAttribute("totalPages", qnaPage.getTotalPages());  // 전체 페이지 수
        model.addAttribute("qnaType1", qnaType1);  // 필터링을 위한 qnaType1 전달
        return "/admin/cs/qna/list";  // 뷰 이름
    }

    // Qna 상세 보기 페이지
    @GetMapping("/admin/cs/qna/write/{qnaId}")
    public String showQnaDetail(@PathVariable long qnaId, Model model) {
        QnaDTO qna = qnaService.getQnaById(qnaId);  // Qna ID로 Qna 조회
        model.addAttribute("qna", qna);  // Qna DTO를 모델에 추가
        return "/admin/cs/qna/write";  // 상세 보기 페이지로 이동
    }

    // 답변 제출
    @PostMapping("/admin/cs/qna/answer/{qnaId}")
    public String submitAnswer(@PathVariable long qnaId, @RequestParam String answer) {
        // 실제 답변 저장 로직 호출
        qnaService.answerQna(qnaId, answer); // 이 줄이 반드시 필요합니다!

        log.info("Qna ID: {}, 답변 내용: {}", qnaId, answer);
        return "redirect:/admin/cs/qna/list";
    }

}

