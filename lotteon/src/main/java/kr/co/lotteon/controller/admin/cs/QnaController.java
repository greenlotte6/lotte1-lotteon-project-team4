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

import java.util.List;

@Slf4j
@Controller
public class QnaController {

    @Autowired
    private QnaService qnaService;

    // Qna 목록 조회 (페이지네이션, qnaType1 필터링)
    @GetMapping("/admin/cs/qna/list")
    public String getQnaList(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(required = false) String qnaType1,
                             @RequestParam(required = false) String qnaType2,
                             Model model) {

        System.out.println(qnaType1);
        System.out.println(qnaType2);

        Page<QnaDTO> qnaPage;

        if (qnaType1 != null && !qnaType1.isEmpty()) {
            qnaPage = qnaService.getQnaListByType(qnaType1, page, size);
            log.info("🔍 qnaType1 필터 적용: {}", qnaType1);
        } else {
            qnaPage = qnaService.getQnaPage(page, size);
            log.info("📄 전체 QnA 목록 조회 - page: {}, size: {}", page, size);
        }

        // Qna 리스트 출력 로그
        for (QnaDTO qna : qnaPage.getContent()) {
            log.info("📝 QnaID: {}, Title: {}, Type1: {}, Type2: {}, Date: {}, User: {}",
                    qna.getQnaid(),
                    qna.getTitle(),
                    qna.getQnaType1(),
                    qna.getQnaType2(),
                    qna.getDate(),
                    (qna.getUid() != null ? qna.getUid() : "null"));
        }

        model.addAttribute("qnaList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        model.addAttribute("qnaType1", qnaType1);
        model.addAttribute("qnaType2", qnaType1);

        return "/admin/cs/qna/list";
    }

    // Qna 상세 조회 (편집 화면)
    @GetMapping("/admin/cs/qna/write/{qnaid}")
    public String showQnaDetail(@PathVariable long qnaid, Model model) {
        QnaDTO qna = qnaService.getQnaById(qnaid);
        model.addAttribute("qna", qna);
        return "/admin/cs/qna/write";
    }

    // 답변 제출
    @PostMapping("/admin/cs/qna/answer/{qnaid}")
    public String submitAnswer(@PathVariable long qnaid, @RequestParam String answer) {
        // 답변 저장 로직
        qnaService.answerQna(qnaid, answer);
        log.info("Qna ID: {}, 답변 내용: {}", qnaid, answer);
        return "redirect:/admin/cs/qna/list";
    }

    // Qna 삭제
    @PostMapping("/admin/cs/qna/delete")
    public String deleteQnas(@RequestParam(value = "selectedQnas", required = false) List<Long> selectedQnas,
                             @RequestParam(value = "deleteAll", required = false) String deleteAll) {
        if (deleteAll != null) {
            // 전체 삭제 처리
            qnaService.deleteAllQnas();
            log.info("📤 전체 QnA 삭제");
        } else if (selectedQnas != null && !selectedQnas.isEmpty()) {
            // 선택된 항목 삭제 처리
            qnaService.deleteQnasByIds(selectedQnas);
            log.info("📤 선택된 QnA 삭제: {}", selectedQnas);
        } else {
            log.info("❌ 삭제할 항목이 없습니다.");
        }
        return "redirect:/admin/cs/qna/list";
    }

}
