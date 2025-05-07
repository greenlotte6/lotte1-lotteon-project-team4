package kr.co.lotteon.dto.OAuth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2DTO oAuth2DTO;

    public CustomOAuth2User(OAuth2DTO oAuth2DTO) {
        this.oAuth2DTO = oAuth2DTO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return oAuth2DTO.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {

        return oAuth2DTO.getName();
    }

    public String getUsername() {

        return oAuth2DTO.getUsername();
    }
}