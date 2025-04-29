package kr.co.lotteon.controller.admin;


import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.service.SellerService;
import kr.co.lotteon.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class PassController {

    private final UsersService usersService;


    @GetMapping("/uid/{uid}")
    public Map<String, Integer> checkUid(@PathVariable String uid) {
        int count = usersService.countByUid(uid);
        Map<String, Integer> result = new HashMap<>();
        result.put("count", count);
        return result;
    }


    @GetMapping("/hp/{hp}")
    public Map<String, Integer> checkHp(@PathVariable String hp) {
        int count = usersService.countByHp(hp);
        Map<String, Integer> result = new HashMap<>();
        result.put("count", count);
        return result;
    }

    @GetMapping("/user/email/{email}")
    public Map<String, Integer> checkEmail(@PathVariable String email) {
        int count = usersService.countByEmail(email);

        if (count == 1) {
            usersService.sendEmailCode(email); // 인증코드 전송
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("count", count);
        return result;
    }


    @PostMapping("/user/email/auth")
    @ResponseBody
    public boolean emailAuth(@RequestBody Map<String, String> requestBody, HttpSession session) {
        String inputCode = requestBody.get("authCode");
        String sessionCode = (String) session.getAttribute("authCode");

        return inputCode != null && inputCode.equals(sessionCode);
    }

    private final SellerService sellerService;


    @PostMapping("/member/find/id")
    @ResponseBody
    public Map<String, Object> findUserId(@RequestParam("uname") String uname,
                                          @RequestParam("email") String email) {
        Map<String, Object> result = new HashMap<>();

        Optional<Users> userOpt = usersService.findByNameAndEmail(uname, email);

        if (userOpt.isPresent()) {
            result.put("status", "success");
            result.put("uname", userOpt.get().getUname());
        } else {
            result.put("status", "fail");
            result.put("message", "일치하는 회원 정보를 찾을 수 없습니다.");
        }

        return result;
    }







}