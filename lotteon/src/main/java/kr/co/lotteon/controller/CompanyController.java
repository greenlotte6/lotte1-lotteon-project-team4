package kr.co.lotteon.controller;

import kr.co.lotteon.dto.MediaDTO;
import kr.co.lotteon.entity.Recruit;
import kr.co.lotteon.service.RecruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor
public class CompanyController {

    private final RecruitService recruitService;


    @GetMapping("/company/culture")
    public String culture() {
        return "/company/culture";
    }

    @GetMapping("/company/index")
    public String index() {
        return "/company/index";
    }

    @GetMapping("/company/media")
    public String media() {
        return "/company/media";
    }


    @GetMapping("/company/recruit")
    public String recruit(@RequestParam(defaultValue = "") String searchType,
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

        System.out.println(recruitPage.getContent());
        System.out.println(recruitPage.getContent());
        System.out.println(recruitPage.getContent());
        System.out.println(recruitPage.getContent());

        model.addAttribute("recruitList", recruitPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", recruitPage.getTotalPages());

        model.addAttribute("search", search);
        model.addAttribute("searchType", searchType);


        return "/company/recruit";
    }




    @GetMapping("/company/story")
    public String story() {
        return "/company/story";
    }



}
