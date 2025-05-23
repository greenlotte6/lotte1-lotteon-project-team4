package kr.co.lotteon.security;

import kr.co.lotteon.OAuth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {

        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, CustomLoginFailureHandler failureHandler) throws Exception {

        //로그인 설정
        http.formLogin(login -> login
                .loginPage("/member/login")
                .failureHandler(failureHandler)
                .defaultSuccessUrl("/")
                .usernameParameter("uid")
                .passwordParameter("password"));




        //로그아웃 설정
        http.logout(logout -> logout.logoutUrl("/member/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/member/login?code=101"));

      /*
            인가 설정
             - MyUserDetails 권한 목록 생성하는 메서드 (getAuthorities)
             에서 접두어로 ROLE_ 입력해야 hasRole, hasAnyRole 권한 처리됨
             - Spring Security는 기본적으로 인가 페이지에 대해
                                login 페이지로 redirect 수행


       */

        //인가 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/product/cart/**", "/product/order/**", "/product/complete/**", "/qna/write/**", "/myaccount/**").authenticated()
                .anyRequest().permitAll()
        );

        //oauth2
//        http
//                .oauth2Login((oauth2) -> oauth2
//                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
//                                .userService(customOAuth2UserService)));
        http
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )

                );


        //기타 보안 설정
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
