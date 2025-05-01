package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "point")
public class Point {

    private int no;

    @Id
    private int point_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid") // 외래키로 "uid"를 사용
    private Users users;

    private int given;
    private int balance;
    private String details;

    @CreationTimestamp
    private LocalDate given_date;

}
