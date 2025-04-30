package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @GetMapping("/admin/cs/qna/list")
    public String getQnaList(Model model) {
        List<Qna> qnaList = qnaService.getQnaList(); // 문의 리스트 가져오기
        model.addAttribute("qnaList", qnaList);
        // 모델에 추가
        return "/admin/cs/qna/list";  // Thymeleaf 템플릿 파일 경로
    }

    @GetMapping("/admin/cs/qna/view")
    public String view() {
        return "/admin/cs/qna/view";
    }

    @GetMapping("/admin/cs/qna/write")
    public String write() {
        return "/admin/cs/qna/write";
    }
}
