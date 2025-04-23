package kr.co.lotteon.controller.admin.cs;

import kr.co.lotteon.entity.Recruit;
import kr.co.lotteon.service.RecruitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RecruitController {

    private final RecruitService recruitService;

    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }

    // 채용공고 목록 페이지
    @GetMapping("/admin/cs/recruit/list")
    public String list() {
        return "/admin/cs/recruit/list";
    }

    @PostMapping("/admin/cs/recruit/register")
    public String registerRecruit(@RequestParam String title,
                                  @RequestParam String department,
                                  @RequestParam String experience,
                                  @RequestParam String type,
                                  @RequestParam String startDate,
                                  @RequestParam String endDate,
                                  @RequestParam String content,
                                  Model model) {

        Recruit recruit = new Recruit();
        recruit.setTitle(title);
        recruit.setDepartment(department);
        recruit.setExperience(experience);
        recruit.setType(type);
        recruit.setStartDate(LocalDate.parse(startDate));
        recruit.setEndDate(LocalDate.parse(endDate));
        recruit.setContent(content);

        recruitService.saveRecruit(recruit);  // 여기서 저장됨

        model.addAttribute("message", "채용등록이 완료되었습니다.");

        return "redirect:/admin/cs/recruit/list";
    }
}
