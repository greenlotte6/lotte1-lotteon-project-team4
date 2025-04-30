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

    @GetMapping("/email/{email}")
    public Map<String, Integer> checkEmail(@PathVariable String email) {
        int count = usersService.countByEmail(email);

        if (count == 0) {
            usersService.sendEmailCode(email); // 인증코드 전송
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("count", count);
        return result;
    }


    @PostMapping("/email/auth")
    @ResponseBody
    public boolean emailAuth(@RequestBody Map<String, String> requestBody, HttpSession session) {
        String inputCode = requestBody.get("authCode");
        String sessionCode = (String) session.getAttribute("authCode");


        if (sessionCode == null || inputCode == null || inputCode.isBlank()) {
            return false;
        }

        return inputCode.equals(sessionCode);
    }


    @PostMapping("/email/find")
    @ResponseBody
    public Map<String, Object> sendEmailCodeForFind(@RequestBody Map<String, String> params) {
        String uname = params.get("uname");
        String email = params.get("email");

        Map<String, Object> result = new HashMap<>();
        Optional<Users> userOpt = usersService.findByNameAndEmail(uname, email);

        if (userOpt.isPresent()) {
            usersService.sendEmailCode(email);
            result.put("status", "success");
        } else {
            result.put("status", "fail");
        }

        return result;
    }

    @GetMapping("/email/find/{email}")
    public Map<String, Integer> sendEmailCodeForFind(@PathVariable String email) {
        int count = usersService.countByEmail(email);

        if (count == 1) {
            usersService.sendEmailCode(email); // 인증코드 전송
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("count", count);
        return result;
    }





}