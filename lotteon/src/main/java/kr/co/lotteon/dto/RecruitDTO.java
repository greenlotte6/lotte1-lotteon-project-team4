package kr.co.lotteon.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class RecruitDTO {

    private Long jobId; // 구인 정보의 고유 ID
    private String department; // 부서명
    private String experience; // 경력 요구 사항
    private String type; // 구인 유형
    private String title; // 구인 공고 제목
    private String writer; // 공고 작성자
    private String status; // 구인 공고 상태
    private String period;
    private LocalDate startDate; // 구인 공고 시작일
    private LocalDate endDate; // 구인 공고 종료일
    private LocalDateTime regDate; // 구인 공고 등록일
    private String content; // 구인 공고의 상세 내용

}
