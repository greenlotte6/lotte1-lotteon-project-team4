package kr.co.lotteon.dto;

import kr.co.lotteon.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDTO {
    private int qnaid;
    private String userUid;
    private String qnaType1;
    private String qnaType2;
    private String title;
    private String content;
    private LocalDate creationDate;
    private LocalDateTime date;
    private String status;
    private String answer;
    private String writer;

    // 추가 필드
    private String uname;
}
