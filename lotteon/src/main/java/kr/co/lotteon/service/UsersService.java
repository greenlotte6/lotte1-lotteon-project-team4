package kr.co.lotteon.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.SellerDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.SellerRepository;
import kr.co.lotteon.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
//import lombok.Value;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession session;
    private final SellerRepository sellerRepository;

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
                .role("USER")              // 기본값 부여
                .status("정상")          // 기본값 부여
                .grade("basic")            // 등급도 기본 지정 가능
                .point(0)
                .build();

        usersRepository.save(user);
    }

    public Users login(String uid, String password) {
        return usersRepository.findByUid(uid)
                .filter(user -> passwordEncoder.matches(password, user.getPassword())) //
                .orElse(null);
    }

    public Optional<Users> findById(String uid) {
        return usersRepository.findById(uid);
    }

    public int countByUid(String uid) {
        return usersRepository.countByUid(uid);
    }


    public int countByHp(String hp) {
        return usersRepository.countByHp(hp);
    }

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public int countByEmail(String email) {
        return usersRepository.countByEmail(email);
    }

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

            session.setAttribute("authCode", String.valueOf(code)); // 세션 저장

            return String.valueOf(code);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
                .role("SELLER") // 판매자 역할 지정
                .build();

        sellerRepository.save(seller);
    }


    public long countByAid(String aid) {
        return sellerRepository.countByAid(aid);
    }

    // UsersService
    public Optional<Users> findByNameAndEmail(String uname, String email) {
        return usersRepository.findByUnameAndEmail(uname, email);
    }


    public Optional<Users> findByUidAndEmail(String uid, String email) {
        return usersRepository.findByUidAndEmail(uid, email);
    }

    public void save(Users user) {
        usersRepository.save(user);
    }

    // UsersService.java
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







}



