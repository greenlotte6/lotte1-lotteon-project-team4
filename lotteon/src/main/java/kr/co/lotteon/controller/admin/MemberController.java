package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.service.PointService;
import kr.co.lotteon.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Console;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final UsersService usersService;
    private final PointService pointService;

    @GetMapping("/admin/member/list")
    public String list(Model model) {

        List<UsersDTO> users = usersService.findAll();
        model.addAttribute("users", users);

        log.info("users: {}", users);

        // 회원 목록 불러오기
        return "/admin/member/list";
    }

//    @GetMapping("/admin/member/point")
//    public String point(Model model) {
//
//        List<PointDTO> pointDTOList = pointService.UserJoinPointAll();
//
//        model.addAttribute("points", pointDTOList);
//        log.info("points: {}", pointDTOList);
//
//        return "/admin/member/point";
//    }



}
