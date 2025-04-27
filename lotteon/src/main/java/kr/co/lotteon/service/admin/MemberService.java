package kr.co.lotteon.service.admin;

import kr.co.lotteon.dao.MemberMapper;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Point;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.PointRepository;
import kr.co.lotteon.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

        return result.stream()
                        .map(m -> modelMapper.map(m, UsersDTO.class))
                        .collect(Collectors.toList());
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

    public UsersDTO modify(UsersDTO usersDTO) {

        Optional<Users> optUsers = usersRepository.findById(usersDTO.getUid());

        Users users = null;
        if (optUsers.isPresent()) {
            users = optUsers.get();

            users.setGrade(usersDTO.getGrade());

            usersRepository.save(users);
        }

        log.info("아이디 확인 : {}", usersDTO.getUid());
        log.info("등급 확인 : {}", usersDTO.getGrade());

        return modelMapper.map(users, UsersDTO.class);

    }

}
