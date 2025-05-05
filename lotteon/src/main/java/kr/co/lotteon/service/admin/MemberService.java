package kr.co.lotteon.service.admin;

import com.querydsl.core.Tuple;
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

        log.info("users!!!!!!!!!: {}", usersDTOList);

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

    // 포인트 목록 조회
    public PageResponseDTO<PointDTO> selectPoint(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> tuplePage = pointRepository.selectPoint(pageable);

        List<PointDTO> pointDTOList = tuplePage.getContent().stream().map(tuple -> {

            Point point = tuple.get(0, Point.class);
            String uid = tuple.get(1, String.class);
            String uname = tuple.get(2, String.class);

            PointDTO pointDTO = modelMapper.map(point, PointDTO.class);
            pointDTO.setUid(uid);
            pointDTO.setUname(uname);

            log.info("pointDTO: {}", pointDTO);
            log.info("pointDTO: {}", pointDTO);
            log.info("pointDTO: {}", pointDTO);

            return pointDTO;
        }).toList();


        // 전체 게시물 갯수
        int total = (int) tuplePage.getTotalElements();

        return PageResponseDTO
                .<PointDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(pointDTOList)
                .total(total)
                .build();

    }

    // 포인트 목록 검색
    public PageResponseDTO<PointDTO> searchPoint(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> tuplePage = pointRepository.searchPoint(pageRequestDTO, pageable);

        List<PointDTO> pointDTOList = tuplePage.getContent().stream().map(tuple -> {
            Point point = tuple.get(0, Point.class);
            String uid = tuple.get(1, String.class);
            String uname = tuple.get(2, String.class);

            PointDTO pointDTO = modelMapper.map(point, PointDTO.class);
            pointDTO.setUid(uid);
            pointDTO.setUname(uname);

            return pointDTO;

        }).toList();

        // 전체 게시물 갯수
        int total = (int) tuplePage.getTotalElements();

        return PageResponseDTO
                .<PointDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(pointDTOList)
                .total(total)
                .build();
    }

    // 포인트 삭제
    public void delete(List<Integer> point_id) {

        memberMapper.deletePoint(point_id);
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

//    // 회원 등급 선택 수정
//    public void modify(UsersDTO usersDTO) {
//
//        Optional<Users> optUsers = usersRepository.findById(usersDTO.getUid());
//
//        Users users;
//        if (optUsers.isPresent()) {
//            users = optUsers.get();
//
//            users.setGrade(usersDTO.getGrade());
//
//            usersRepository.save(users);
//
//            log.info("users 확인 : {}", users);
//        }
//
//
//    }

    // 회원 수정 모달창 정보 불러오기
    public UsersDTO getModifyModal(String uid) {
        Optional<Users> optUsers = usersRepository.findByUid(uid);

        if (optUsers.isPresent()) {
            Users users = optUsers.get();
            UsersDTO usersDTO = modelMapper.map(users, UsersDTO.class);
            usersDTO.setUid(users.getUid());

            log.info("usersDTO: {}", usersDTO);
            log.info("usersDTO: {}", usersDTO);
            log.info("usersDTO: {}", usersDTO);

            return usersDTO;

        } else {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }

//        return usersRepository.findById(uid)
//                .map(users -> modelMapper.map(users, UsersDTO.class))
//                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
    }


//    // 회원 정보 전체 수정
//    public void modifyModal(UsersDTO usersDTO) {
//        Optional<Users> optUsers = usersRepository.findById(usersDTO.getUid());
//
//        if (optUsers.isPresent()) {
//            Users users = optUsers.get();
//
//            users.updateDTO(usersDTO);
//            users.setUname(usersDTO.getUname());
//            users.setGender(usersDTO.getGender());
//            users.setGrade(usersDTO.getGrade());
//            users.setStatus(usersDTO.getStatus());
//            users.setEmail(usersDTO.getEmail());
//            users.setHp(usersDTO.getHp());
//            users.setZip(usersDTO.getZip());
//            users.setAddr1(usersDTO.getAddr1());
//            users.setAddr2(usersDTO.getAddr2());
//            users.setU_created_at(usersDTO.getU_created_at());
//            users.setU_last_login(usersDTO.getU_last_login());
//
//            log.info("users {}", users);
//
//            usersRepository.save(users);
//        }
//    }


}