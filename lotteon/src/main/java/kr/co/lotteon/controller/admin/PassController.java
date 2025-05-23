package kr.co.lotteon.controller.admin;


import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.service.SellerService;
import kr.co.lotteon.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            usersService.sendEmailCode(email); // 인증코드 전송
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




    @PostMapping("/user/email/find-id")
    public Map<String, Object> findUserForId(@RequestBody Map<String, String> request, HttpSession session) {
        String uid = request.get("uid");
        String email = request.get("email");
        Optional<Users> user = usersService.findByUidAndEmail(uid, email);

        Map<String, Object> result = new HashMap<>();
        if (user.isPresent()) {
            session.setAttribute("verifiedUid", uid); // ✅ 서버 세션에 저장
            result.put("status", "success");
        } else {
            result.put("status", "fail");
            result.put("message", "사용자 정보를 찾을 수 없습니다.");
        }
        return result;
    }

    @PostMapping("/email/find-pw")
    @ResponseBody
    public Map<String, Object> findUserForPw(@RequestBody Map<String, String> requestBody, HttpSession session) {
        String uid = requestBody.get("uid");
        String email = requestBody.get("email");

        Optional<Users> userOpt = usersService.findByUidAndEmail(uid, email);

        Map<String, Object> result = new HashMap<>();
        if (userOpt.isPresent()) {
            session.setAttribute("verifiedUid", uid); //
            result.put("status", "success");
        } else {
            result.put("status", "fail");
            result.put("message", "아이디 또는 이메일이 올바르지 않습니다.");
        }

        return result;
    }










}