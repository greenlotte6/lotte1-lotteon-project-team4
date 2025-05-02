package kr.co.lotteon.security;

import kr.co.lotteon.entity.Users;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
public class MyUserDetails implements UserDetails {  // 인증 객체

    private final Users users;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // 권한 목록 생성 = 리소스 권한 목록
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + users.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() { // 사용자 비밀번호
        return users.getPassword();
    }

    @Override
    public String getUsername() { // 사용자 아이디
        return users.getUid();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 만료 여부
        // 계정 만료 여부(true: 만료안됨, false: 만료됨)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정 잠김 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정 활성화 여부
        return true;
    }



}