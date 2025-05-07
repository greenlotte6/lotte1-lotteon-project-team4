package kr.co.lotteon.dto;

import kr.co.lotteon.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDTO {
    private long qnaid;
    private String uid;
    private String qnaType1;
    private String qnaType2;
    private String title;
    private String content;
    private LocalDateTime date;
    private String status;
    private String answer;
    private String writer;
}
