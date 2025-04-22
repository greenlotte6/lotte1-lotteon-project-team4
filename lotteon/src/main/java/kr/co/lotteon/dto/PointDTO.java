package kr.co.lotteon.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointDTO {

    private int no;
    private int point_id;
    private String Users_uid;
    private int given;
    private int balance;
    private String details;
    private LocalDateTime given_date;

}
