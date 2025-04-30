package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "Users")
public class Users {

    @Id
    private String uid;
    private int no;
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

    @DateTimeFormat
    private LocalDate birth;



    @CreationTimestamp
    private LocalDateTime u_created_at;

    private String status;

    @CreationTimestamp
    private LocalDateTime u_last_login;

    private String mgmt;
    private String other;


}
