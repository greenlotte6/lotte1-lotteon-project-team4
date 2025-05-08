package kr.co.lotteon.security;

import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.Users;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class MyUserDetails implements UserDetails, OAuth2User {  // 인증 객체

    private final Users users;
    private Seller seller;
    private Map<String, Object> attributes;

    public MyUserDetails(Users users, Seller seller, Map<String, Object> attributes) {

        this.users = users;
        this.seller = seller;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (users != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + users.getRole()));
        } else if (seller != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + seller.getRole()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return users != null ? users.getPassword() : seller.getPassword();
    }

    @Override
    public String getUsername() {
        return users != null ? users.getUid() : seller.getAid();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() {
        if (users != null) {
            return "정상".equals(users.getStatus());
        }
        return false;
    }

}