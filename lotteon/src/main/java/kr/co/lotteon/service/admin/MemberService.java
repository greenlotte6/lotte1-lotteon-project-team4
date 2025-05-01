package kr.co.lotteon.service.admin;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.co.lotteon.dao.MemberMapper;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Point;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.PointRepository;
import kr.co.lotteon.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PointRepository pointRepository;
    private Users users;

    // 회원 목록 조회
    public PageResponseDTO<UsersDTO> findAll(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Users> usersPage = usersRepository.findAll(pageable);

        List<UsersDTO> usersDTOList = usersPage.getContent().stream()
                .map(user -> modelMapper.map(user, UsersDTO.class))
                .collect(Collectors.toList());

        log.info("users: {}", usersDTOList);

        int total = (int) usersPage.getTotalElements();

        return PageResponseDTO
                .<UsersDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(usersDTOList)
                .total(total)
                .build();
    }

    // 회원 목록 검색
    public PageResponseDTO<UsersDTO> searchMembers(PageRequestDTO pageRequestDTO) {

        String searchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Users> usersPage;

        if ("uid".equals(searchType)) {
            usersPage = usersRepository.findByUidContaining(keyword, pageable);
        } else if ("uname".equals(searchType)) {
            usersPage = usersRepository.findByUnameContaining(keyword, pageable);
        } else if ("email".equals(searchType)) {
            usersPage = usersRepository.findByEmailContaining(keyword, pageable);
        } else if ("hp".equals(searchType)) {
            usersPage = usersRepository.findByHpContaining(keyword, pageable);
        } else {
            usersPage = usersRepository.findAll(pageable);
        }

        List<UsersDTO> usersDTOList = usersPage.getContent().stream()
                                                                .map(user -> modelMapper.map(user, UsersDTO.class))
                                                                .collect(Collectors.toList());

        int total = (int) usersPage.getTotalElements();

        return PageResponseDTO
                .<UsersDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(usersDTOList)
                .total(total)
                .build();

    }

    public List<PointDTO> selectPoint() {

        return memberMapper.selectPoint();

    }

    public void delete(List<Integer> point_id) {

        memberMapper.deletePoint(point_id);
    }


    public List<PointDTO> searchPoint(PointDTO pointDTO) {
        List<Point> pointList = new ArrayList<>();

        switch (pointDTO.getSearchType()) {
            case "uid" -> pointList = pointRepository.findByUsersUidContaining(pointDTO.getKeyword());
            case "uname" -> pointList = pointRepository.findByUsersUnameContaining(pointDTO.getKeyword());
            case "email" -> pointList = pointRepository.findByUsersEmailContaining(pointDTO.getKeyword());
            case "hp" -> pointList = pointRepository.findByUsersHpContaining(pointDTO.getKeyword());
        }

        return pointList.stream()
                .map(point -> {
                    PointDTO dto = modelMapper.map(point, PointDTO.class);
                    dto.setUid(point.getUsers().getUid());
                    dto.setUname(point.getUsers().getUname());
                    return dto;
                }).collect(Collectors.toList());
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

    // 회원 수정
    public void modify(UsersDTO usersDTO) {

        Optional<Users> optUsers = usersRepository.findById(usersDTO.getUid());

        Users users;
        if (optUsers.isPresent()) {
            users = optUsers.get();

            users.setGrade(usersDTO.getGrade());

            usersRepository.save(users);

            log.info("users 확인 : {}", users);
        }


    }

}