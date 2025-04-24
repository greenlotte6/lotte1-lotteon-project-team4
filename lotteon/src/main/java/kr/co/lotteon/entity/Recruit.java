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
@ToString
@Builder
public class Recruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id") //
    private Long id;  //

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
    private String period;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime regDate;

    @Lob
    private String content;

    @Column(nullable = false)
    private LocalDateTime date;

    @PrePersist
    protected void onCreate() {
        this.regDate = LocalDateTime.now();
        if (this.date == null) {
            this.date = LocalDateTime.now(); // 기본값 설정
        }
    }
}