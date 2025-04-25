package kr.co.lotteon.service;

import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

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
                .password(dto.getPassword())
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



}
