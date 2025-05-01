package kr.co.lotteon.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointDTO {

    private int no;
    private int point_id;
    private String uid;
    private int given;
    private int balance;
    private String details;
    private LocalDate given_date;

    // 사용자 정보
    private String uname;

    private String searchType;
    private String keyword;

}
