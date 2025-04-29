package kr.co.lotteon.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
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
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession session;
    private final SellerRepository sellerRepository;

    public List<UsersDTO> findAll() {
        List<Users> usersList = usersRepository.findAll();

        log.info("users: {}", usersList);

        return usersList.stream()
                .map(users -> modelMapper.map(users, UsersDTO.class))
                .collect(Collectors.toList());
    }

    public void saveUser(UsersDTO dto) {
        Users user = Users.builder()
                .uid(dto.getUid())
                .uname(dto.getUname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .hp(dto.getHp())
                .addr1(dto.getAddr1())
                .addr2(dto.getAddr2())
                .zip(dto.getZip())
                .role("USER")              // 기본값 부여
                .status("ACTIVE")          // 기본값 부여
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
    private String sender; // 메일 보내는 사람 (application.properties에서 가져옴)

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
                .password(passwordEncoder.encode(dto.getPassword())) // 비밀번호 암호화!!
                .company(dto.getCompany())
                .ceo(dto.getCeo())
                .biz_num(dto.getBiz_num())
                .osn(dto.getOsn())
                .number(dto.getNumber())
                .fax(dto.getFax())
                .addr(dto.getAddr())
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

    public boolean isEmailExists(String email) {
        return usersRepository.countByEmail(email) > 0;
    }


}



