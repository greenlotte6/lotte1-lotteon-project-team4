package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDTO {

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
    private LocalDateTime u_created_at;
    private String status;
    private LocalDateTime u_last_login;
    private String mgmt;
    private String other;

}
