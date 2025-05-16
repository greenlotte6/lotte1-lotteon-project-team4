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
    private int rating;
    private String comment;

    @CreationTimestamp
    private LocalDateTime write_at;


}
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer rid;


    @Column(name = "Products_pid", nullable = false)
    private Integer productsPid;

    @Column(name = "Users_uid", length = 255)
    private String usersUid;

    private Integer rating;

    @Column(length = 255)
    private String comment;

    @Column(name = "write_at")
    private LocalDateTime writeAt;
}
