package kr.co.lotteon.controller.admin;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.UsersRepository;
import kr.co.lotteon.service.UsersService;
import kr.co.lotteon.service.admin.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class  MemberController {

    private final MemberService memberService;
    private final UsersRepository usersRepository;

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

    @PostMapping("/admin/member/update-grade")
    @ResponseBody
    public String updateUserGrades(@RequestBody List<UserGradeDTO> dtos) {
        log.info("받은 등급 리스트: {}", dtos);
        try {
            for (UserGradeDTO dto : dtos) {
                memberService.updateGrade(dto.getUid(), dto.getGrade());
            }
            return "success";
        } catch (Exception e) {
            log.error("등급 수정 실패", e);
            return "fail";
        }
    }

    @PostMapping("/admin/member/update-info")
    @ResponseBody
    public String updateUserInfo(@RequestBody UsersDTO dto) {
        try {
            memberService.updateUserInfo(dto);
            return "success";
        } catch (Exception e) {
            log.error("회원 수정 실패", e);
            return "fail";
        }
    }


    // 회원 수정 모달창 정보 불러오기
    @ResponseBody
    @GetMapping("/admin/member/getModifyModal")
    public UsersDTO getModifyModal(@RequestParam("uid") String uid) {
        Users user = usersRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        return UsersDTO.builder()
                .uid(user.getUid())
                .uname(user.getUname())
                .gender(user.getGender())
                .status(user.getStatus())
                .email(user.getEmail())
                .grade(user.getGrade())
                .hp(user.getHp())
                .zip(user.getZip())
                .addr1(user.getAddr1())
                .addr2(user.getAddr2())
                .u_created_at(user.getU_created_at())
                .u_last_login(user.getU_last_login())
                .build(); // 수정 모달을 띄우는 페이지로 리턴
    }

    @ResponseBody
    @PostMapping("/admin/member/update-status")
    public String updateUserStatus(@RequestBody Map<String, String> request) {
        try {
            memberService.updateStatus(request.get("uid"), request.get("status"));
            return "success";
        } catch (Exception e) {
            log.error("회원 상태 변경 실패", e);
            return "fail";
        }
    }


//    // 회원 정보 전체 수정
//    @PostMapping("/admin/member/modifyModal")
//    public String modifyModal(@ModelAttribute UsersDTO usersDTO) {
//
//        memberService.modifyModal(usersDTO);
//
//        return "redirect:/admin/member/list";
//    }

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