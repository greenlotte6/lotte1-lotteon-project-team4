package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.entity.Faq;
import kr.co.lotteon.service.FaqService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FaqController {

    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping("/admin/cs/faq/list")
    public String list(Model model) {
        List<Faq> faqList = faqService.getAllFaqs();
        model.addAttribute("faqList", faqList);
        return "/admin/cs/faq/list";
    }

    @GetMapping("/admin/cs/faq/modify")
    public String modifyFaq(@RequestParam("id") int id, Model model) {
        Faq faq = faqService.getFaqById(id);
        model.addAttribute("faq", faq);
        return "/admin/cs/faq/modify"; // 수정페이지 HTML
    }

    @PostMapping("/admin/cs/faq/modify")
    public String updateFaq(@ModelAttribute Faq faq) {
        faqService.updateFaq(faq);
        return "redirect:/admin/cs/faq/list"; // 수정 후 목록으로 이동
    }

    @GetMapping("/admin/cs/faq/view")
    public String viewFaq(@RequestParam("faqId") int faqId, Model model) {
        Faq faq = faqService.getFaqById(faqId);
        model.addAttribute("faq", faq);
        return "/admin/cs/faq/view";
    }


    @GetMapping("/admin/cs/faq/write")
    public String write() {
        return "/admin/cs/faq/write";
    }

    @PostMapping("/admin/cs/faq/write")
    public String submitFaq(@RequestParam("type1") String type1,
                            @RequestParam("type2") String type2,
                            @RequestParam("title") String title,
                            @RequestParam("content") String content) {

        Faq faq = new Faq();
        faq.setType1(type1);
        faq.setType2(type2);
        faq.setTitle(title);
        faq.setContent(content);
        faq.setMgmt("관리자"); // 관리 정보 예시
        faq.setCateIcon("Q"); // 아이콘 정보 예시

        faqService.saveFaq(faq);
        return "redirect:/admin/cs/faq/list";
    }

}
