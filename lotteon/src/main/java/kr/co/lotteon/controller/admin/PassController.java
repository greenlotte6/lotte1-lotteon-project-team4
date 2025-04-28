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


    @GetMapping("/email/{email}")
    public Map<String, Integer> checkEmail(@PathVariable String email) {
        int count = usersService.countByEmail(email);
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

    @PostMapping("/email/auth")
    public Map<String, Boolean> emailAuth(@RequestBody Map<String, String> request, HttpSession session) {
        String inputCode = request.get("authCode"); // 사용자가 입력한 코드
        String sessionCode = (String) session.getAttribute("authCode"); // 서버에 저장된 코드

        Map<String, Boolean> result = new HashMap<>();
        result.put("status", inputCode.equals(sessionCode)); // 둘이 일치하면 true, 아니면 false
        return result;
    }
}