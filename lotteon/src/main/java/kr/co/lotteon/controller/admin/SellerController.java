package kr.co.lotteon.controller.admin;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/seller") // 모든 seller 관련 요청 prefix
public class SellerController {

    private final SellerService sellerService; // SellerService 주입


    @GetMapping("/aid/{aid}")
    @ResponseBody
    public Map<String, Object> checkAid(@PathVariable("aid") String aid) {
        long count = sellerService.countByAid(aid);
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        return result;
    }

    @PostMapping("/login")
    public String loginSeller(@RequestParam("userType") String aid,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model) {

        Seller seller = sellerService.loginSeller(aid, password);

        if (seller != null) {
            session.setAttribute("seller", seller); // 세션 저장
            return "redirect:/"; // 로그인 성공 시 메인 이동
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호를 확인하세요.");
            return "/member/login"; // 로그인 실패 시 다시 로그인 페이지
        }
    }
}

