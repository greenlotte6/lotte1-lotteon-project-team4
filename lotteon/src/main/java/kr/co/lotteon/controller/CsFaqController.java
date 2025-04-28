package kr.co.lotteon.controller;

import kr.co.lotteon.entity.Faq;
import kr.co.lotteon.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CsFaqController {

    private final FaqService faqService;

    @GetMapping("/faq/member-list")
    public String memberlist(Model model) {
        // "회원" 카테고리에서 각 서브 타입별 FAQ 가져오기
        List<Faq> joinFaqs = faqService.getFaqsByCategoryAndSubType("회원", "가입");
        List<Faq> withdrawFaqs = faqService.getFaqsByCategoryAndSubType("회원", "탈퇴");
        List<Faq> loginFaqs = faqService.getFaqsByCategoryAndSubType("회원", "로그인");
        List<Faq> infoFaqs = faqService.getFaqsByCategoryAndSubType("회원", "회원정보");


        // 모델에 FAQ 데이터 추가
        model.addAttribute("joinFaqs", joinFaqs);
        model.addAttribute("withdrawFaqs", withdrawFaqs);
        model.addAttribute("loginFaqs", loginFaqs);
        model.addAttribute("infoFaqs", infoFaqs);

        return "/cs/faq/member-list";  // 사용자 페이지
    }

    @GetMapping("/faq/coupon-list")
    public String couponlist(Model model) {
        List<Faq> earnFaqs = faqService.getFaqsByCategoryAndSubType("쿠폰/혜택/이벤트", "쿠폰/할인혜택");
        List<Faq> pointFaqs = faqService.getFaqsByCategoryAndSubType("쿠폰/혜택/이벤트", "포인트");
        List<Faq> coalitionFaqs = faqService.getFaqsByCategoryAndSubType("쿠폰/혜택/이벤트", "제휴");
        List<Faq> eventFaqs = faqService.getFaqsByCategoryAndSubType("쿠폰/혜택/이벤트", "이벤트");

        model.addAttribute("earnFaqs", earnFaqs);
        model.addAttribute("pointFaqs", pointFaqs);
        model.addAttribute("coalitionFaqs", coalitionFaqs);
        model.addAttribute("eventFaqs", eventFaqs);

        return "/cs/faq/coupon-list";
    }

    @GetMapping("/faq/order-list")
    public String orderlist(Model model) {
        List<Faq> goodsFaqs = faqService.getFaqsByCategoryAndSubType("주문/결제", "상품");
        List<Faq> paymentFaqs = faqService.getFaqsByCategoryAndSubType("주문/결제", "결제");
        List<Faq> historyFaqs = faqService.getFaqsByCategoryAndSubType("주문/결제", "구매내역");
        List<Faq> receiptFaqs = faqService.getFaqsByCategoryAndSubType("주문/결제", "영수증/증빙");

        model.addAttribute("goodsFaqs", goodsFaqs);
        model.addAttribute("paymentFaqs", paymentFaqs);
        model.addAttribute("historyFaqs", historyFaqs);
        model.addAttribute("receiptFaqs", receiptFaqs);

        return "/cs/faq/order-list";
    }


    @GetMapping("/faq/delivery")
    public String delivery(Model model) {
        List<Faq> periodFaqs = faqService.getFaqsByCategoryAndSubType("배송", "배송상태/기간");
        List<Faq> changeFaqs = faqService.getFaqsByCategoryAndSubType("배송", "배송정보확인/변경");
        List<Faq> OverseasshippingFaqs = faqService.getFaqsByCategoryAndSubType("배송", "해외배송");
        List<Faq> SamedaydeliveryFaqs = faqService.getFaqsByCategoryAndSubType("배송", "당일배송");
        List<Faq> directpurchaseFaqs = faqService.getFaqsByCategoryAndSubType("배송", "해외직구");


        model.addAttribute("periodFaqs", periodFaqs);
        model.addAttribute("changeFaqs", changeFaqs);
        model.addAttribute("OverseasshippingFaqs", OverseasshippingFaqs);
        model.addAttribute("SamedaydeliveryFaqs", SamedaydeliveryFaqs);
        model.addAttribute("directpurchaseFaqs", directpurchaseFaqs);
        return "/cs/faq/delivery";
    }

    @GetMapping("/faq/return-list")
    public String retunelist(Model model) {

        List<Faq> ReturnrequesFaqs = faqService.getFaqsByCategoryAndSubType("취소/반품/교환", "반품신청/철회");
        List<Faq> CheckreturninformationFaqs = faqService.getFaqsByCategoryAndSubType("취소/반품/교환", "반품정보확인/변경");
        List<Faq> ApplyforexchangeAS = faqService.getFaqsByCategoryAndSubType("취소/반품/교환", "교환 AS신청/철회");
        List<Faq> CheckexchangeinformationFaqs = faqService.getFaqsByCategoryAndSubType("취소/반품/교환", "교환정보확인/변경");
        List<Faq> CancellationrequestFaqs = faqService.getFaqsByCategoryAndSubType("취소/반품/교환", "취소신청/철회");
        List<Faq> CancelConfirmFaqs = faqService.getFaqsByCategoryAndSubType("취소/반품/교환", "취소확인/환불정보");

        System.out.println(ApplyforexchangeAS);

        model.addAttribute("ReturnrequesFaqs", ReturnrequesFaqs);
        model.addAttribute("CheckreturninformationFaqs", CheckreturninformationFaqs);
        model.addAttribute("ApplyforexchangeAS", ApplyforexchangeAS);
        model.addAttribute("CheckexchangeinformationFaqs", CheckexchangeinformationFaqs);
        model.addAttribute("CancellationrequestFaqs", CancellationrequestFaqs);
        model.addAttribute("CancelConfirmFaqs", CancelConfirmFaqs);
        return "/cs/faq/retune-list";
    }

    @GetMapping("/faq/trip-list")
    public String triplist(Model model) {
        List<Faq> travelFaqs = faqService.getFaqsByCategoryAndSubType("여행/숙박/항공", "여행/숙박");
        List<Faq> aviationFaqs = faqService.getFaqsByCategoryAndSubType("여행/숙박/항공", "항공");

        model.addAttribute("travelFaqs", travelFaqs);
        model.addAttribute("aviationFaqs", aviationFaqs);

        return "/cs/faq/trip-list";
    }

    @GetMapping("/faq/safy")
    public String safy(Model model) {
        List<Faq> ServiceFaqs = faqService.getFaqsByCategoryAndSubType("안전거래", "서비스 이용규칙 위반");
        List<Faq> knowledgeFaqs = faqService.getFaqsByCategoryAndSubType("안전거래", "지식재산권침해");
        List<Faq> postFaqs = faqService.getFaqsByCategoryAndSubType("안전거래", "게시물 정책위반");
        List<Faq> directFaqs  = faqService.getFaqsByCategoryAndSubType("안전거래", "직거래/외부거래유도");
        List<Faq> displayFaqs = faqService.getFaqsByCategoryAndSubType("안전거래", "표시광고");
        List<Faq> youthFaqs = faqService.getFaqsByCategoryAndSubType("안전거래", "청소년 위해상품/이미지");

        model.addAttribute("ServiceFaqs", ServiceFaqs);
        model.addAttribute("knowledgeFaqs", knowledgeFaqs);
        model.addAttribute("postFaqs", postFaqs);
        model.addAttribute("directFaqs", directFaqs);
        model.addAttribute("displayFaqs", displayFaqs);
        model.addAttribute("youthFaqs", youthFaqs);
        return "/cs/faq/safy";
    }

    @GetMapping("/faq/view/{id}")
    public String viewFaq(@PathVariable int id, Model model) {
        // id로 FAQ 조회
        Faq faq = faqService.getFaqById(id);

        // FAQ 정보를 모델에 추가
        model.addAttribute("faq", faq);

        // 상세보기 페이지로 이동
        return "/cs/faq/view";
    }

}
