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
@Table(name = "support")
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_id")
    private int supportId;

    private String phone;

    private String hours;

    private String email;

    private String dispute;
}
