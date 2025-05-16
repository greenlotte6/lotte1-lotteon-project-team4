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
public class ReviewDTO {

    private int review_id;
    private int Products_pid;
    private String Users_uid;
    private int rating;
    private String comment;
    private LocalDateTime write_at;

}
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