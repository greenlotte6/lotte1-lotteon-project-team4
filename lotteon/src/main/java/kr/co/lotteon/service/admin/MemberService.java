package kr.co.lotteon.service.admin;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dao.MemberMapper;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Point;
import kr.co.lotteon.entity.QPoint;
import kr.co.lotteon.entity.QUsers;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.PointRepository;
import kr.co.lotteon.repository.custom.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final PointRepository pointRepository;
    private final UserRepositoryCustom userRepositoryCustom;
    private final ModelMapper modelMapper;
    private QUsers qUsers = QUsers.users;

    public List<PointDTO> selectPoint() {

        return memberMapper.selectPoint();

    }

    public void delete(List<Integer> point_id) {

        memberMapper.deletePoint(point_id);
    }

    // 사용자 검색 서비스
    public Page<UsersDTO> getUsers(String searchType, String keyword, PageRequestDTO pageRequestDTO, Pageable pageable) {

        // 1. selectAllForSearch 메소드 호출하여 검색 결과 가져오기
        Page<Tuple> result = userRepositoryCustom.selectAllForSearch(searchType, keyword, pageable);

        // 2. 검색된 데이터를 DTO로 변환
        List<UsersDTO> userList = result.getContent().stream().map(tuple -> {
                    Users users = tuple.get(0, Users.class);
                    int point_id = tuple.get(1, Integer.class);

                    UsersDTO usersDTO = modelMapper.map(users, UsersDTO.class);
                    usersDTO.setPoint_id(point_id);
                    return usersDTO;
                }).toList();
        int total = (int) result.getTotalElements();

        log.info("userList: {}", userList);

        // 3. 변환된 DTO 리스트와 페이징 정보를 사용해 Page 객체 생성
        return new PageImpl<>(userList, pageable, result.getTotalElements());
    }
}
