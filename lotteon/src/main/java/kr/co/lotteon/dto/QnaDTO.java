package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


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
    private LocalDate date;
    private String status;
    private String answer;
}
