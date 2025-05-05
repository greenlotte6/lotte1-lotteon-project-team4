package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "OAuth2")
public class OAuth2 {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String role;

    @Column(unique = true)
    private String name;

    private String username;
    private String email;

}
