package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeId;

    @Column(name = "notice_type")
    private String noticeType;
    private String title;
    private String content;
    private String mgmt;

    private int hits;

    @Column(name = "upload_at")
    private LocalDate uploadAt;


    //    private String mgmt = "admin";


    @PrePersist
    public void prePersist() {
        if (this.mgmt == null) {
            this.mgmt = "admin";
        }
        if (this.uploadAt == null) {
            this.uploadAt = LocalDate.now();  // 날짜 자동 설정
        }
    }
}