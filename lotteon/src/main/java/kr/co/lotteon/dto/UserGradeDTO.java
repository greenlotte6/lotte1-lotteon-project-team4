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
public class UserGradeDTO {

    private String uid;
    private String grade;

}