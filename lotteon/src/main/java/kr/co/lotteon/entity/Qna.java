package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

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
    private Integer qnaId;

    @Column(name = "Users_uid", length = 12, nullable = false)
    private String userUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Users_uid", insertable = false, updatable = false)
    private Users users;

    @Column(name = "qna_type_1", length = 5, nullable = false)
    private String qnaType1;

    @Column(name = "qna_type_2", length = 5, nullable = false)
    private String qnaType2;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 5, nullable = false)
    private String status;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String answer;
}