package kr.co.lotteon.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.QPoint;
import kr.co.lotteon.entity.QUsers;
import kr.co.lotteon.repository.custom.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QPoint qPoint = QPoint.point;
    private QUsers qUsers = QUsers.users;

//    // 회원 목록 검색 메서드
//    @Override
//    public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO) {
//
//        String searchType = pageRequestDTO.getSearchType();
//        String keyword = pageRequestDTO.getKeyword();
//
//        Pageable pageable = pageRequestDTO.getPageable("no");
//
//        // 검색 조건에 따라서 where 조건 표현식 생성
//        BooleanExpression expression = null;
//
//        if(searchType.equals("uid")){ // 제목 검색
//            expression = qUsers.uid.contains(keyword); // where title = contain 'keyword'
//        }else if(searchType.equals("uname")){
//            expression = qUsers.uname.contains(keyword);
//        }else if(searchType.equals("email")){
//            expression = qUsers.email.contains(keyword);
//        }else if(searchType.equals("hp")){
//            expression = qUsers.hp.contains(keyword);
//        }
//
//        List<Tuple> tupleList = queryFactory // 페이징 된 리스트
//                .select(qPoint, qUsers)
//                .from(qPoint)
//                .join(qUsers)
//                .on(qPoint.users.uid.eq(qUsers.uid))
//                .where(expression)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .orderBy(qPoint.no.desc())
//                .fetch();
//
//        long total = queryFactory
//                .select(qPoint.count())
//                .from(qPoint)
//                .join(qUsers)
//                .on(qPoint.users.uid.eq(qUsers.uid))
//                .where(expression)
//                .fetchOne();
//
//        // 페이징 처리를 위한 페이지 객체 반환
//        return new PageImpl<Tuple>(tupleList, pageable, total);
//    }
}
