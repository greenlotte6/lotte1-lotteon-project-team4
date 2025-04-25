package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.service.UsersService;
import kr.co.lotteon.service.admin.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final UsersService usersService;
    private final MemberService memberService;

    @GetMapping("/admin/member/list")
    public String list(Model model) {

        List<UsersDTO> userList = usersService.findAll();
        model.addAttribute("userList", userList);

        log.info("users: {}", userList);

        // 회원 목록 불러오기
        return "/admin/member/list";
    }

    //검색
    @GetMapping("/admin/member/search")
    public String search(@RequestParam("searchType") String searchType,
                         @RequestParam("keyword") String keyword,
                         Model model) {

        List<UsersDTO> userList;

        if (searchType != null && keyword != null) {
            userList = memberService.searchMembers(searchType, keyword);
            log.info("검색한 유저 리스트: {}", userList);
        } else {
            userList = usersService.findAll();
            log.info("모든 유저 리스트: {}", userList);
        }

        model.addAttribute("userList", userList);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "/admin/member/list";
    }

    @GetMapping("/admin/member/point")
    public String point(Model model) {

        List<PointDTO> points = memberService.selectPoint();
        model.addAttribute("points", points);

        //log.info("point: {}", points);

        return "/admin/member/point";
    }

//    @GetMapping("/admin/member/modify")
//    public String modify(String uid, Model model) {
//
//        Users user = memberService.modify(uid);
//
//        model.addAttribute("user", user);
//
//        return "/admin/member/list";
//    }

    @PostMapping("/admin/member/postModify")
    public String modify(UsersDTO usersDTO) {

        memberService.modify(usersDTO);

        return "redirect:/admin/member/list";
    }

    @PostMapping("/admin/member/delete")
    public String delete(@RequestParam("point_id") List<Integer> point_id) {
        // 서비스에서 삭제 처리
        memberService.delete(point_id);

        log.info("delete: {}", point_id);

        // 삭제 후 포인트 목록 페이지로 리다이렉트
        return "redirect:/admin/member/point";
    }


}
