package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.entity.Faq;
import kr.co.lotteon.repository.FaqRepository;
import kr.co.lotteon.service.FaqService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class FaqController {

    private final FaqService faqService;
    private final FaqRepository faqRepository;

    public FaqController(FaqService faqService, FaqRepository faqRepository) {
        this.faqService = faqService;
        this.faqRepository = faqRepository;
    }

    @GetMapping("/admin/cs/faq/list")
    public String listFaqs(Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(required = false) String type1,
                           @RequestParam(required = false) String type2) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("faqId").descending());
        Page<Faq> faqPage;

        if (type1 != null && type2 != null) {
            faqPage = faqRepository.findByType1AndType2(type1, type2, pageRequest);
        } else if (type1 != null) {
            faqPage = faqRepository.findByType1(type1, pageRequest);
        } else {
            faqPage = faqRepository.findAll(pageRequest);
        }

        model.addAttribute("faqList", faqPage.getContent());
        model.addAttribute("totalPages", faqPage.getTotalPages());
        model.addAttribute("currentPage", page);

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
