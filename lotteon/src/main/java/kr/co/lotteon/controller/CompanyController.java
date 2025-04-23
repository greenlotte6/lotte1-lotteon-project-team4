package kr.co.lotteon.controller;

import kr.co.lotteon.dto.MediaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor
public class CompanyController {


    @GetMapping("/company/culture")
    public String culture() {
        return "/company/culture";
    }

    @GetMapping("/company/index")
    public String index() {
        return "/company/index";
    }

    @GetMapping("/company/media")
    public String media(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "6") int size,
                        Model model) {

        // 더미 데이터
        List<MediaDTO> allVideos = List.of(
                new MediaDTO("제목1", "설명1", "https://youtube.com/1", "img_1.png"),
                new MediaDTO("제목2", "설명2", "https://youtube.com/2", "img_2.png"),
                new MediaDTO("제목3", "설명3", "https://youtube.com/3", "img_3.png"),
                new MediaDTO("제목4", "설명4", "https://youtube.com/4", "img_4.png"),
                new MediaDTO("제목5", "설명5", "https://youtube.com/5", "img_5.png"),
                new MediaDTO("제목6", "설명6", "https://youtube.com/6", "img_6.png"),
                new MediaDTO("제목7", "설명7", "https://youtube.com/7", "img_7.png")
                // 필요한 만큼 추가
        );

        // page와 size가 음수일 경우 기본값을 사용하도록 처리
        if (page < 0) page = 0;
        if (size <= 0) size = 6; // size가 0 이하일 경우 기본 6으로 설정

        int start = page * size;
        int end = Math.min(start + size, allVideos.size());

        // 인덱스 범위 체크 및 예외 처리
        if (start >= allVideos.size()) {
            start = allVideos.size() - 1; // 마지막 페이지 처리
            end = allVideos.size();
        }

        // 서브리스트를 이용하여 페이지별 데이터 생성
        List<MediaDTO> pagedList = allVideos.subList(start, end);

        // 전체 페이지 수 계산 (0페이지부터 시작한다고 가정)
        int totalPages = (int) Math.ceil((double) allVideos.size() / size);

        model.addAttribute("mediaList", pagedList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

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
