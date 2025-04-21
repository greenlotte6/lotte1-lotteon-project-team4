package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final UsersService usersService;

//    @GetMapping("/admin/member/list")
//    public String list(String uid, Model model) {
//
//        UsersDTO usersDTO = usersService.findAll(uid);
//        model.addAttribute("users", users);
//
//        // 회원 목록 불러오기
//        return "users";
//    }

//    @GetMapping("/article/view")
//    public String view(int no, Model model) {
//
//        // 글 조회 서비스 호출
//        ArticleDTO articleDTO = articleService.findById(no);
//
//        model.addAttribute(articleDTO);
//
//        return "/article/view";
//    }

    @GetMapping("/admin/member/point")
    public String point() {
        return "/admin/member/point";
    }

}
