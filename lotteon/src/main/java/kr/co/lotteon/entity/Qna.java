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
@Table(name = "qna")  // DB 테이블 이름과 맞춤
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private long qnaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Users_uid")
    private Users user;

    @Column(name = "qna_type_1", length = 255, nullable = false)
    private String qnaType1;

    @Column(name = "qna_type_2", length = 255, nullable = false)
    private String qnaType2;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime date;

    @Column(length = 255, nullable = false)
    private String status;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String answer;

    private String writer;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = "검토중";
        }
    }
}
