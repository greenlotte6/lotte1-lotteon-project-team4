package kr.co.lotteon.controller.admin;


import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/email/{email}")
    public Map<String, Integer> checkEmail(@PathVariable String email) {
        int count = usersService.countByEmail(email);

        if (count == 0) {
            usersService.sendEmailCode(email); // 중복 없으면 인증코드 발송
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





}