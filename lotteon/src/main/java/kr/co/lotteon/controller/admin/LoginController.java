package kr.co.lotteon.controller.admin;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.SellerDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.service.PolicyService;
import kr.co.lotteon.service.SellerService;
import kr.co.lotteon.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
public class LoginController {

    @GetMapping("/member/login")
    public String login() {
        return "/member/login";
    }

    private final SellerService sellerService;

    @PostMapping("/member/login")
    public String login(@RequestParam("id") String id,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        // 1. 일반 회원 로그인 먼저 시도
        Users user = usersService.login(id, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }

        // 2. 일반회원 로그인 실패 → 판매자 로그인 시도
        Seller seller = sellerService.loginSeller(id, password);
        if (seller != null) {
            session.setAttribute("seller", seller);
            return "redirect:/";
        }

        // 3. 모두 실패
        model.addAttribute("error", "아이디 또는 비밀번호를 확인하세요.");
        return "/member/login";
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
    public String registerSeller(Model model) {
        model.addAttribute("sellerDTO", new SellerDTO());
        return "/member/registerSeller";
    }

    @PostMapping("/member/registerSeller")
    public String registerSeller(@ModelAttribute("sellerDTO") SellerDTO sellerDTO) {
        usersService.saveSeller(sellerDTO);
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


    @GetMapping("/member/such")
    public String such() {
        return "/member/such";
    }

    @PostMapping("/member/such")
    public String findId(@RequestParam String uname, @RequestParam String email, Model model) {
        Optional<Users> userOpt = usersService.findByNameAndEmail(uname, email);

        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "/member/such"; // such.html 로 이동
        } else {
            model.addAttribute("error", "일치하는 회원이 없습니다.");
            return "/member/find"; // 다시 찾기 페이지
        }
    }



}
