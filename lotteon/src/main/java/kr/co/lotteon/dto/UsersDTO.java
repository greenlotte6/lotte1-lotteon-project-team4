package kr.co.lotteon.dto;

import kr.co.lotteon.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    private String birth;

    public UsersDTO(String uid, String uname, String birth, String email, String hp, String zip, String addr1, String addr2) {
        this.uid = uid;
        this.uname = uname;
        this.birth = birth;
        this.email = email;
        this.hp = hp;
        this.zip = zip;
        this.addr1 = addr1;
        this.addr2 = addr2;
    }
    public String getMaskedUname() {
        if (uname == null || uname.length() < 2) return uname;

        int length = uname.length();
        if (length == 2) {
            return uname.charAt(0) + "*";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(uname.charAt(0));
            for (int i = 1; i < length - 1; i++) {
                sb.append("*");
            }
            sb.append(uname.charAt(length - 1));
            return sb.toString();
        }
    }

    public String getMaskedUid() {
        if (uid == null || uid.length() < 4) return uid;

        int length = uid.length();
        return uid.substring(0, length - 4) + "****";
    }

    // 추가 필드
    private int point_id;
    private boolean selected;

    // OAuth2 로그인
    private String provider;
    private String providerId;

    public Users toEntity() {
        return Users.builder()
                .uid(uid)
                .no(no)
                .uname(uname)
                .password(password)
                .gender(gender)
                .grade(grade)
                .point(point)
                .email(email)
                .hp(hp)
                .zip(zip)
                .addr1(addr1)
                .addr2(addr2)
                .role(role)
                .u_created_at(u_created_at)
                .status(status)
                .u_last_login(u_last_login)
                .mgmt(mgmt)
                .other(other)
                .build();
    }

}