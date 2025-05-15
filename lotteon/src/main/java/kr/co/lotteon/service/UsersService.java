package kr.co.lotteon.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import kr.co.lotteon.dto.SellerDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.SellerRepository;
import kr.co.lotteon.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession session;
    private final SellerRepository sellerRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    // ✅ 사용자 정보 조회 by userId(uid)
    public UsersDTO getUserInfoByUserId(String userId) {
        Users user = usersRepository.findByUid(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new UsersDTO(
                user.getUid(),
                user.getUname(),
                user.getBirth(),
                user.getEmail(),
                user.getHp(),
                user.getZip(),
                user.getAddr1(),
                user.getAddr2()
        );
    }

    // 회원 정보 수정
    public void updateUserInfo(String uid, String email, String hp, String addr1, String addr2, String zip, HttpSession session) {
        Optional<Users> optionalUser = usersRepository.findByUid(uid);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            user.setEmail(email);
            user.setHp(hp);
            user.setAddr1(addr1);
            user.setAddr2(addr2);
            user.setZip(zip);

            session.setAttribute("user", user);
            usersRepository.save(user);
            usersRepository.flush();

            log.info("기존값 vs 새값");
            log.info("email: {} -> {}", user.getEmail(), email);
            log.info("hp: {} -> {}", user.getHp(), hp);
            log.info("addr1: {} -> {}", user.getAddr1(), addr1);
            log.info("addr2: {} -> {}", user.getAddr2(), addr2);
            log.info("zip: {} -> {}", user.getZip(), zip);

            log.info("DB에 사용자 정보 저장 완료: {}", user);
        }
    }

    // 회원 가입
    public void saveUser(UsersDTO dto) {
        Users user = Users.builder()
                .uid(dto.getUid())
                .uname(dto.getUname())
                .gender(dto.getGender())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .hp(dto.getHp())
                .addr1(dto.getAddr1())
                .addr2(dto.getAddr2())
                .zip(dto.getZip())
                .birth(dto.getBirth())
                .role("USER")
                .status("정상")
                .grade("basic")
                .point(0)
                .build();

        usersRepository.save(user);
    }

    // 로그인
    public Users login(String uid, String password) {
        return usersRepository.findByUid(uid)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(null);
    }

    // 비밀번호 변경
    public void updatePassword(String uid, String rawPassword) {
        Optional<Users> optUser = usersRepository.findByUid(uid);
        if (optUser.isPresent()) {
            Users user = optUser.get();
            String encryptedPassword = passwordEncoder.encode(rawPassword);
            user.setPassword(encryptedPassword);
            usersRepository.save(user);
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
    }

    // 중복 체크 (아이디)
    public int countByUid(String uid) {
        return usersRepository.countByUid(uid);
    }

    // 중복 체크 (전화번호)
    public int countByHp(String hp) {
        return usersRepository.countByHp(hp);
    }

    // 중복 체크 (이메일)
    public int countByEmail(String email) {
        return usersRepository.countByEmail(email);
    }

    // 이메일 인증 코드 전송
    public String sendEmailCode(String receiver) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
            String subject = "인증코드 안내";
            String content = "<h1>인증코드 : " + code + "</h1>";

            message.setFrom(new InternetAddress(sender, "LotteON", "UTF-8"));
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(subject);
            message.setContent(content, "text/html;charset=UTF-8");

            mailSender.send(message);

            session.setAttribute("authCode", String.valueOf(code));
            return String.valueOf(code);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 판매자 등록
    public void saveSeller(SellerDTO dto) {
        Seller seller = Seller.builder()
                .aid(dto.getAid())
                .password(passwordEncoder.encode(dto.getPassword()))
                .company(dto.getCompany())
                .ceo(dto.getCeo())
                .biz_num(dto.getBiz_num())
                .osn(dto.getOsn())
                .number(dto.getNumber())
                .fax(dto.getFax())
                .zip(dto.getZip())
                .addr1(dto.getAddr1())
                .addr2(dto.getAddr2())
                .role("SELLER")
                .build();

        sellerRepository.save(seller);
    }

    // 판매자 아이디 중복 체크
    public long countByAid(String aid) {
        return sellerRepository.countByAid(aid);
    }

    // 이름과 이메일로 사용자 찾기
    public Optional<Users> findByNameAndEmail(String uname, String email) {
        return usersRepository.findByUnameAndEmail(uname, email);
    }

    // 아이디와 이메일로 사용자 찾기
    public Optional<Users> findByUidAndEmail(String uid, String email) {
        return usersRepository.findByUidAndEmail(uid, email);
    }

    // 사용자 저장
    public void save(Users user) {
        usersRepository.save(user);
    }

    public Optional<Users> findById(String uid) {
        return usersRepository.findById(uid); //
    }


}
