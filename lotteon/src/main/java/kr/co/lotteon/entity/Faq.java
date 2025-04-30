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
@Table(name = "faq")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer faqId;

    private String type1;
    private String type2;
    private String title;
    private String content;
    private Integer hits;

    @CreationTimestamp
    private LocalDate uploadDate;

//    private String mgmt = "admin";
    private String cateIcon;
    private String answer;
    private String category;

}
