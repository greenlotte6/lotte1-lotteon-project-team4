package kr.co.lotteon.controller.admin;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.service.PolicyService;
import kr.co.lotteon.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Controller
public class LoginController {

    @GetMapping("/member/login")
    public String login() {
        return "/member/login";
    }


    @PostMapping("/member/login")
    public String login(@RequestParam("id") String uid,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        Users user = usersService.login(uid, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/"; // 로그인 성공
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호를 확인하세요."); // 에러 메시지
            return "/member/login"; // 다시 로그인 폼
        }
    }
    private final UsersService usersService;




    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "정상적으로 로그아웃 되었습니다!");
        return "redirect:/";
    }


    @GetMapping("/member/register")
    public String registerForm(Model model) {
        model.addAttribute("usersDTO", new UsersDTO());
        return "/member/register";
    }



    @PostMapping("/member/register")
    public String register(@ModelAttribute("usersDTO") UsersDTO usersDTO) {
        usersService.saveUser(usersDTO);
        return "redirect:/member/login";
    }



    private final PolicyService policyService;

    @GetMapping("/member/terms")
    public String policySellerExtra(Model model) {
        model.addAttribute("terms", policyService.getTermsSet(1));
        return "/member/terms";
    }

    @GetMapping("/member/registerSeller")
    public String registerSeller() {
        return "/member/registerSeller";

    }

    @PostMapping("/member/registerSeller")
    public String registerSeller(@ModelAttribute("usersDTO") UsersDTO usersDTO) {
        usersService.saveUser(usersDTO);
        return "redirect:/member/login";
    }

    @GetMapping("/member/join")
    public String join() {
        return "/member/join";

    }

    @GetMapping("/member/find")
    public String find() {
        return "/member/find";

    }

    @GetMapping("/member/updatepw")
    public String updatepw () {

        return "/member/updatepw";
    }

    @GetMapping("/member/resultid")
    public String resultid() {
        return "/member/resultid";
    }





}
