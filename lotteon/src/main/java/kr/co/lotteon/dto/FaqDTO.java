package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaqDTO {

    private int faqId;
    private String type1;
    private String type2;
    private String title;
    private int hits;
    private LocalDate uploadDate;
    private String uploadDateStr;

}
