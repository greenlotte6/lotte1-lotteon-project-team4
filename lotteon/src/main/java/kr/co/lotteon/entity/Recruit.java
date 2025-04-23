package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "job")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(length = 50)
    private String department;

    @Column(length = 50)
    private String experience;

    @Column(length = 20)
    private String type;

    @Column(length = 200)
    private String title;

    @Column(length = 50)
    private String writer;

    @Column(length = 20)
    private String status;

    @Column(length = 50)
    private String period; // 🔥 빠졌던 근무 기간 필드 추가

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime regDate;

    @Lob
    private String content;

    @PrePersist
    protected void onCreate() {
        this.regDate = LocalDateTime.now();
    }
}
