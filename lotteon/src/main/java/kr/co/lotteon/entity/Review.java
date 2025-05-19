package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "review")
public class Review {

    @Id
    private int review_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Products_pid")
    private Products products;

    private String Users_uid;
    private Double rating;
    private String comment;

    @CreationTimestamp
    private LocalDateTime write_at;


}