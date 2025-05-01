package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "copyright")
public class Copyright {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "copyright_id")
    private int copyrightId;

    private String content;


}
