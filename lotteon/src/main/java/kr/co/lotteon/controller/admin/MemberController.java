package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.dto.UsersDTO;
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

        List<UsersDTO> users = usersService.findAll();
        model.addAttribute("users", users);

        log.info("users: {}", users);

        // 회원 목록 불러오기
        return "/admin/member/list";
    }

    @GetMapping("/admin/member/search")
    public String search(@RequestParam(required = false) String searchType,
                         @RequestParam(required = false) String keyword,
                         PageRequestDTO pageRequestDTO,
                         Pageable pageable,
                         Model model) {

        // 서비스 호출하여 페이징 처리된 사용자 목록을 반환
        Page<UsersDTO> usersPage = memberService.getUsers(searchType, keyword, pageRequestDTO, pageable);

        // 모델에 결과를 담아서 반환
        model.addAttribute("users", usersPage);  // 페이징된 사용자 목록만 추가
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        log.info("searchType: {}", searchType);
        log.info("keyword: {}", keyword);
        log.info("pageRequestDTO: {}", pageRequestDTO);
        log.info("pageable: {}", pageable);

        return "/admin/member/searchList";
    }

    @GetMapping("/admin/member/point")
    public String point(Model model) {

        List<PointDTO> points = memberService.selectPoint();
        model.addAttribute("points", points);

        //log.info("point: {}", points);

        return "/admin/member/point";
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
