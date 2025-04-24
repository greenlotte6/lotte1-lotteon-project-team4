package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.entity.Recruit;
import kr.co.lotteon.service.RecruitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RecruitController {

    private final RecruitService recruitService;

    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }

    // 채용공고 목록 페이지 (검색, 페이징 포함)
    @GetMapping("/admin/cs/recruit/list")
    public String getRecruitList(@RequestParam(defaultValue = "") String searchType,
                                 @RequestParam(defaultValue = "") String search,
                                 @RequestParam(defaultValue = "1") int page,
                                 Model model) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        Page<Recruit> recruitPage;

        if (search == null || search.isBlank()) {
            recruitPage = recruitService.findAllRecruits(pageRequest);
        } else {
            recruitPage = recruitService.searchRecruits(searchType, search, pageRequest);
        }

        model.addAttribute("recruitList", recruitPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", recruitPage.getTotalPages());

        model.addAttribute("search", search);
        model.addAttribute("searchType", searchType);


        return "/admin/cs/recruit/list";
    }




    @PostMapping("/admin/cs/recruit/register")
    public String registerRecruit(@RequestParam String title,
                                  @RequestParam String department,
                                  @RequestParam String experience,
                                  @RequestParam String type,
                                  @RequestParam("startDate") String startDate,
                                  @RequestParam("endDate") String endDate,
                                  @RequestParam String content,
                                  Model model) {

        Recruit recruit = new Recruit();

        // 오늘 날짜를 기준으로 상태를 설정
        LocalDate currentDate = LocalDate.now();
        LocalDate parsedEndDate = LocalDate.parse(endDate);

        // 마감일이 현재 날짜 이후면 "모집중", 그렇지 않으면 "마감"
        if (currentDate.isBefore(parsedEndDate) || currentDate.isEqual(parsedEndDate)) {
            recruit.setStatus("모집중");
        } else {
            recruit.setStatus("마감");
        }

        recruit.setTitle(title);
        recruit.setDepartment(department);
        recruit.setExperience(experience);
        recruit.setType(type);
        recruit.setStartDate(LocalDate.parse(startDate));
        recruit.setEndDate(parsedEndDate);
        recruit.setContent(content);

        recruitService.saveRecruit(recruit);

        model.addAttribute("message", "채용등록이 완료되었습니다.");

        return "redirect:/admin/cs/recruit/list";
    }


    @PostMapping("/admin/cs/recruit/delete")
    @ResponseBody
    public Map<String, Object> deleteRecruit(@RequestParam List<Long> jobIds) {
        Map<String, Object> response = new HashMap<>();
        try {
            recruitService.deleteRecruits(jobIds);
            response.put("success", true); // 성공 응답
        } catch (Exception e) {
            response.put("success", false); // 실패 응답
            response.put("message", e.getMessage()); // 실패 메시지
        }
        return response;
    }
}
