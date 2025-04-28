package kr.co.lotteon.dto;

import jakarta.persistence.Column;
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
public class NoticeDTO {

    private int noticeId;
    private String noticeType;
    private String title;
    private String content;
    private int hits;
    private LocalDate upload_at;
}
