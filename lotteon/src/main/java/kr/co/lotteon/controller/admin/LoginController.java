package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.service.PolicyService;
import kr.co.lotteon.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {

    @GetMapping("/member/login")
    public String login() {
        return "/member/login";
    }



    @GetMapping("/member/register")
    public String registerForm(Model model) {
        model.addAttribute("usersDTO", new UsersDTO());
        return "/member/register";
    }

    private final UsersService usersService;

    @PostMapping("/member/register")
    public String register(@ModelAttribute("usersDTO") UsersDTO usersDTO) {
        usersService.saveUser(usersDTO);
        return "redirect:/member/login";
    }


    private final PolicyService policyService;

    @GetMapping("/member/terms")
    public String policySellerExtra(Model model) {
        model.addAttribute("terms", policyService.getTermsSet(3));
        return "/member/terms";
    }

    @GetMapping("/member/registerSeller")
    public String registerSeller() {
        return "/member/registerSeller";

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
