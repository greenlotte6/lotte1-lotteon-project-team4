package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Integer rid;
    private Integer productsPid;
    private String usersUid;
    private Integer rating;
    private String comment;
    private LocalDateTime writeAt;
}