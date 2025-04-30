package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.dto.QnaDTO;
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

    /*@GetMapping("/admin/cs/qna/list")
    public String getQnaList(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(required = false) String qnaType1,
                             Model model) {

        int pageSize = 10;

        Page<QnaDTO> qnaPage;
        if (qnaType1 != null && !qnaType1.isEmpty()) {
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            qnaPage = qnaService.getQnaListByType(qnaType1, pageable);
        } else {
            qnaPage = qnaService.getQnaPage(page - 1, pageSize);
        }

        model.addAttribute("qnaList", qnaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", qnaPage.getTotalPages());
        return "/admin/cs/qna/list";
    }

    @GetMapping("/admin/cs/qna/write/{qnaId}")
    public String showQnaDetail(@PathVariable long qnaId, Model model) {
        QnaDTO qna = qnaService.getQnaById(qnaId);  // 수정된 부분
        model.addAttribute("qna", qna);
        return "/admin/cs/qna/write";
    }
*/
    @PostMapping("/admin/cs/qna/answer/{qnaId}")
    public String submitAnswer(@PathVariable long qnaId, @RequestParam String answer) {

        return "redirect:/admin/cs/qna/list";
    }
}
