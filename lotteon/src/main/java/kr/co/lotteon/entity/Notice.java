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

    private int hits;

    @CreationTimestamp
    private LocalDateTime upload_at;
    private String mgmt = "admin";

}
