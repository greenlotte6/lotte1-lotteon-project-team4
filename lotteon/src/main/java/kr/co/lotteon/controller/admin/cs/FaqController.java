package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.dto.FaqDTO;
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

import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
public class FaqController {

    private final FaqService faqService;
    private final FaqRepository faqRepository;

    public FaqController(FaqService faqService, FaqRepository faqRepository) {
        this.faqService = faqService;
        this.faqRepository = faqRepository;
    }

    private List<String> getSubTypesByType1(String type1) {
        if (type1 == null) return List.of(); // null 방지

        return switch (type1) {
            case "회원" -> List.of("가입", "탈퇴", "회원정보", "로그인");
            case "쿠폰/혜택/이벤트" -> List.of("쿠폰/할인혜택", "포인트", "제휴", "이벤트");
            case "주문/결제" -> List.of("상품", "결제", "구매내역", "영수증/증빙");
            case "배송" -> List.of("배송상태/기간", "배송정보확인/변경", "해외배송", "당일배송", "해외직구");
            case "취소/반품/교환" -> List.of("반품신청/철회", "반품정보확인/변경", "교환 AS신청/철회", "교환정보확인/변경", "취소신청/철회", "취소확인/환불정보");
            case "여행/숙박/항공" -> List.of("여행/숙박", "항공");
            case "안전거래" -> List.of("서비스 이용규칙 위반", "지식재산권침해", "게시물 정책위반", "직거래/외부거래유도", "표시광고", "청소년 위해상품/이미지");
            default -> List.of();
        };
    }

    @GetMapping("/admin/cs/faq/list")
    public String listFaqs(Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(required = false) String type1,
                           @RequestParam(required = false) String type2) {

        if (type1 != null && type1.trim().isEmpty()) type1 = null;
        if (type2 != null && type2.trim().isEmpty()) type2 = null;

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("faqId").descending());
        Page<Faq> faqPage;

        if (type1 != null && type2 != null) {
            faqPage = faqRepository.findByType1AndType2(type1, type2, pageRequest);
        } else if (type1 != null) {
            faqPage = faqRepository.findByType1(type1, pageRequest);
        } else {
            faqPage = faqRepository.findAll(pageRequest);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");

        List<FaqDTO> dtoList = faqPage.getContent().stream().map(faq -> {
            FaqDTO dto = new FaqDTO();
            dto.setFaqId(faq.getFaqId());
            dto.setTitle(faq.getTitle());
            dto.setHits(faq.getHits());
            dto.setType1(faq.getType1());
            dto.setType2(faq.getType2());
            dto.setUploadDate(faq.getUploadDate());
            dto.setUploadDateStr(faq.getUploadDate().format(formatter));
            return dto;
        }).toList();

        List<String> subTypes = getSubTypesByType1(type1);

        model.addAttribute("faqList", dtoList);
        model.addAttribute("subTypes", subTypes);
        model.addAttribute("totalPages", faqPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("selectedType1", type1);
        model.addAttribute("selectedType2", type2);

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
        faq.setMgmt("관리자");
        faq.setCateIcon("Q");

        faqService.saveFaq(faq);
        return "redirect:/admin/cs/faq/list";
    }

    @PostMapping("/admin/cs/faq/deleteSelected")
    public String deleteSelected(@RequestParam List<Integer> faqIds) {
        faqService.deleteAllByIds(faqIds);
        return "redirect:/admin/cs/faq/list";
    }

    @GetMapping("/admin/cs/faq/delete")
    public String deleteFaq(@RequestParam int id) {
        faqService.deleteFaq(id);
        return "redirect:/admin/cs/faq/list";
    }

}
