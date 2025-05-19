package kr.co.lotteon.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

    private int review_id;
    private int Products_pid;
    private String productName;
    private String Users_uid;
    private Double rating;
    private String comment;
    private LocalDateTime write_at;

    private MultipartFile  file1;
    private MultipartFile  file2;
    private MultipartFile  file3;

}