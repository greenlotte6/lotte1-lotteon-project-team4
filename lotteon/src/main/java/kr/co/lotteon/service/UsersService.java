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

}
