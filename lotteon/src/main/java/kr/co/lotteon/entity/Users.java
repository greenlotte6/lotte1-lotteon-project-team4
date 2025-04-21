package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "Users")
public class Users {

    @Id
    private String uid;
    private int t_u_num;
    private String uname;
    private String password;
    private String gender;
    private String grade;
    private int point;
    private String email;
    private String hp;
    private String zip;
    private String addr1;
    private String addr2;
    private String role;

    @CreationTimestamp
    private LocalDateTime u_creates_at;

    private String status;

    @CreationTimestamp
    private LocalDateTime u_last_login;

    private String mgmt;
    private String other;

}
