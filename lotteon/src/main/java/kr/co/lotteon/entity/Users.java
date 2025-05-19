package kr.co.lotteon.entity;

import jakarta.persistence.*;
import kr.co.lotteon.dto.UsersDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicUpdate
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
    private String birth;

    @CreationTimestamp
    private LocalDateTime u_created_at;

    @Column(name = "status", nullable = false)
    private String status;


    @CreationTimestamp
    private LocalDateTime u_last_login;

    private String mgmt;
    private String other;

    // OAuth2 로그인
    private String provider;
    private String providerId;
//
//    public enum Role {
//        USER, ADMIN, SELLER
//    }

    public void updateDTO(UsersDTO usersDTO) {
        this.uid = usersDTO.getUid();
        this.no = usersDTO.getNo();
        this.uname = usersDTO.getUname();
        this.password = usersDTO.getPassword();
        this.gender = usersDTO.getGender();
        this.grade = usersDTO.getGrade();
        this.point = usersDTO.getPoint();
        this.email = usersDTO.getEmail();
        this.hp = usersDTO.getHp();
        this.zip = usersDTO.getZip();
        this.addr1 = usersDTO.getAddr1();
        this.addr2 = usersDTO.getAddr2();
        this.role = usersDTO.getRole();
        this.birth = usersDTO.getBirth();
    }

}