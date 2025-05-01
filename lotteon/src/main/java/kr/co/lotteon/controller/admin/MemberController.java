package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.service.UsersService;
import kr.co.lotteon.service.admin.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class  MemberController {

    private final MemberService memberService;

    // 회원 목록 조회
    @GetMapping("/admin/member/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<UsersDTO> pageResponseDTO = memberService.findAll(pageRequestDTO);
        model.addAttribute(pageResponseDTO);

        // 회원 목록 불러오기
        return "/admin/member/list";
    }

    // 회원 목록 검색
    @GetMapping("/admin/member/search")
    public String search(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<UsersDTO> pageResponseDTO = memberService.searchMembers(pageRequestDTO);

        model.addAttribute(pageResponseDTO);

        return "/admin/member/list";
    }

//    // 회원 등급 선택 수정
//    @PostMapping("/admin/member/postModify")
//    public String modifyUsers(UsersDTO usersDTO) {
//        memberService.modify(usersDTO);
//
//        log.info("usersDTO 확인 : {}", usersDTO);
//
//        return "redirect:/admin/member/list";
//    }

//    @PostMapping("/admin/member/postModify")
//    public String modifyUsers(@ModelAttribute UsersDTO usersDTO,
//                              @RequestParam("selectedUsers") List<String> selectedUsers,
//                              @RequestParam("grades") List<String> grades) {
//
//            usersDTO.setUid(usersDTO.getUid());  // 유저의 uid 설정
//            usersDTO.setGrade(usersDTO.getGrade());  // 유저의 grade 설정
//
//            // 해당 유저의 등급 수정
//            memberService.modify(usersDTO);
//
//        // 수정 후, 리스트 페이지로 리다이렉트
//        return "redirect:/admin/member/list";
//    }

    // 회원 정보 전체 수정
    @PostMapping("/admin/member/modifyModal")
    public String modifyModal(@ModelAttribute UsersDTO usersDTO) {

        memberService.modifyModal(usersDTO);

        return "redirect:/admin/member/list";
    }

    // 포인트 목록 조회
    @GetMapping("/admin/member/point")
    public String point(Model model, PageRequestDTO pageRequestDTO) {

        PageResponseDTO<PointDTO> pageResponseDTO = memberService.selectPoint(pageRequestDTO);
        model.addAttribute(pageResponseDTO);

        //log.info("point: {}", points);

        return "/admin/member/point";
    }

    // 포인트 목록 검색
    @GetMapping("/admin/member/pointSearch")
    public String searchPoint(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<PointDTO> pageResponseDTO = memberService.searchPoint(pageRequestDTO);

        log.info("pageResponseDTO {}", pageResponseDTO);

        model.addAttribute(pageResponseDTO);

        return "/admin/member/point";
    }

    // 포인트 삭제
    @PostMapping("/admin/member/delete")
    public String delete(@RequestParam("point_id") List<Integer> point_id) {
        // 서비스에서 삭제 처리
        memberService.delete(point_id);

        log.info("delete: {}", point_id);

        // 삭제 후 포인트 목록 페이지로 리다이렉트
        return "redirect:/admin/member/point";
    }

}
