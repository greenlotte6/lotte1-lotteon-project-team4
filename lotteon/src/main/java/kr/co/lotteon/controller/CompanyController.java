package kr.co.lotteon.controller;

import kr.co.lotteon.entity.VideoEntity;
import kr.co.lotteon.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CompanyController {

    private final VideoService videoService;

    @GetMapping("/culture")
    public String culture() {
        return "/company/culture";
    }

    @GetMapping("/index")
    public String index() {
        return "/company/index";
    }

    @GetMapping("/media")
    public String media() {
        return "/company/media";
    }

    @GetMapping("/mediaPage")
    public String mediaPage(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 5;

        // 페이지에 해당하는 비디오 리스트 가져오기
        Page<VideoEntity> videoEntityPage = videoService.getVideoPage(page, pageSize);

        log.info("Current Page: {}", page);
        log.info("Total Pages: {}", videoEntityPage.getTotalPages());

        // videoPage, videoList, currentPage, totalPages 모델에 추가
        model.addAttribute("videoPage", videoEntityPage);
        model.addAttribute("videoList", videoEntityPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", videoEntityPage.getTotalPages());

        return "/company/media";
    }


    @GetMapping("/recruit")
    public String recruit() {
        return "/company/recruit";
    }

    @GetMapping("/story")
    public String story() {
        return "/company/story";
    }
}
