package kr.co.lotteon.security;

import kr.co.lotteon.entity.Seller;
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
public class MyUserDetails implements UserDetails {

    private Users users;
    private Seller seller;

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
    @Override public boolean isEnabled() { return true; }
}