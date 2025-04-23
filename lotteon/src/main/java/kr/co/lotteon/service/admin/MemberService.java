package kr.co.lotteon.service.admin;

import kr.co.lotteon.dao.MemberMapper;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.entity.Point;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final ModelMapper modelMapper;
    private final MemberMapper memberMapper;

    public List<PointDTO> selectPoint() {

        return memberMapper.selectPoint();

    }

}
