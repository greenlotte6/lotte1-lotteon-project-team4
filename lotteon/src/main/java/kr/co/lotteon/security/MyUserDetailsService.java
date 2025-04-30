package kr.co.lotteon.security;

import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService { // DB에서 select 함, UserDetailsService 재정의

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException { // AuthenticationProvider에서 username을 조회해서 loadUserByUsername로 넘겨줌

        log.info("username : {}", uid);

        // 사용자 조회 - 사용자가 입력한 아이디, 비밀번호는 이전 단계인 AuthenticationProvider 쪽에서(인증) 먼저 수행됨
        // 아이디와 비밀번호로 인증하는 것이 끝나고 이 단계가 실행
        Optional<Users> optUser = usersRepository.findById(uid);

        if(optUser.isPresent()) {

            // Security 사용자 인증 객체 생성
            MyUserDetails myUserDetails = MyUserDetails.builder()
                    .users(optUser.get())
                    .build();

            // 리턴되는 myUserDetails는 Security ContextHolder(security에서 사용하는 session)에 저장
            return myUserDetails;
        }

        return null;
    }


}