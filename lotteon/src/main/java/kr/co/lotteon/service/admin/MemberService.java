package kr.co.lotteon.service.admin;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dao.MemberMapper;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Point;
import kr.co.lotteon.entity.QUsers;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.PointRepository;
import kr.co.lotteon.repository.UsersRepository;
import kr.co.lotteon.repository.custom.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final PointRepository pointRepository;
    private final UserRepositoryCustom userRepositoryCustom;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private QUsers qUsers = QUsers.users;

    public List<PointDTO> selectPoint() {

        return memberMapper.selectPoint();

    }

    public void delete(List<Integer> point_id) {

        memberMapper.deletePoint(point_id);
    }

    public List<UsersDTO> searchMembers(String searchType, String keyword) {
        List<Users> result = new ArrayList<>();

        if ("uid".equals(searchType)) {
            result = usersRepository.findByUidContaining(keyword);
        } else if ("uname".equals(searchType)) {
            result = usersRepository.findByUnameContaining(keyword);
        } else if ("email".equals(searchType)) {
            result = usersRepository.findByEmailContaining(keyword);
        } else if ("hp".equals(searchType)) {
            result = usersRepository.findByHpContaining(keyword);
        }

        return result.stream().map(m -> modelMapper.map(m, UsersDTO.class)).collect(Collectors.toList());
    }

//    public PageResponseDTO searchAll(PageRequestDTO pageRequestDTO) {
//
//        Page<Tuple> pagePoint = userRepositoryCustom.selectAllForSearch(pageRequestDTO);
//
//        // Point Entity 리스트를 ArticleDTO 리스트로 변환
//        List<PointDTO> pointDTOList = pagePoint.getContent().stream().map(tuple -> { // 아티클 엔티티를 아티클DTO로 변환
//
//            Point point = tuple.get(0, Point.class);
//            Users users = tuple.get(1, Users.class);
//
//            return modelMapper.map(point, PointDTO.class);
//
//        }).toList();
//
//        // 전체 게시물 갯수
//        int total = (int) pagePoint.getTotalElements();
//
//        return PageResponseDTO.<PointDTO>builder()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(pointDTOList)
//                .total(total)
//                .build();
//    }

    public void modify(UsersDTO usersDTO) {
        // 선택한 유저 아이디가 있는지 확인
        boolean exists = usersRepository.existsById(usersDTO.getUid());

        // 있으면 수정 데이터를 엔티티에 넣기
        if (exists) {
            Users users = usersDTO.toEntity();
            usersRepository.save(users);
        }
    }
}
