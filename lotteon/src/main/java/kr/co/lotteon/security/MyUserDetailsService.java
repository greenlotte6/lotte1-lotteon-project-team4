package kr.co.lotteon.security;

import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.SellerRepository;
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
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        log.info("로그인 시도 아이디: {}", uid);

        // 1. 일반 사용자 먼저 조회
        Optional<Users> optUser = usersRepository.findById(uid);
        if (optUser.isPresent()) {
            return MyUserDetails.builder()
                    .users(optUser.get())
                    .build();
        }

        // 2. 판매자 조회
        Optional<Seller> optSeller = sellerRepository.findById(uid);
        if (optSeller.isPresent()) {
            return MyUserDetails.builder()
                    .seller(optSeller.get())
                    .build();
        }

        throw new UsernameNotFoundException("해당 아이디를 찾을 수 없습니다: " + uid);
    }


}
