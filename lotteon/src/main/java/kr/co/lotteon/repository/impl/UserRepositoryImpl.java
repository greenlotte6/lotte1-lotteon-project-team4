package kr.co.lotteon.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
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

    @Override
    public Page<Tuple> selectAllForSearch(String searchType, String keyword, Pageable pageable) {

        BooleanBuilder where = new BooleanBuilder();

        // 검색 키워드가 null이거나 비어있지 않으면 조건을 추가
        if (keyword != null && !keyword.trim().isEmpty()) {
            // 대소문자 구분 없이 검색
            if ("uid".equals(searchType)) {
                where.and(qUsers.uid.containsIgnoreCase(keyword));
            } else if ("uname".equals(searchType)) {
                where.and(qUsers.uname.containsIgnoreCase(keyword));
            } else if ("email".equals(searchType)) {
                where.and(qUsers.email.containsIgnoreCase(keyword));
            } else if ("hp".equals(searchType)) {
                where.and(qUsers.hp.containsIgnoreCase(keyword));
            }
        }

        // 조건이 하나도 없다면 모든 사용자 데이터 반환
        if (where.getValue() == null) {
            where.and(qUsers.uid.isNotNull()); // 조건이 없다면 모든 사용자가 포함되도록 설정
        }

        // 쿼리 실행
        List<Tuple> tupleList = queryFactory
                .select(qUsers, qPoint.point_id)
                .from(qPoint)
                .leftJoin(qPoint.users, qUsers)
                .where(where)               // 조건 추가
                .orderBy(qUsers.u_created_at.desc()) // 가입일 기준 내림차순 정렬
                .offset(pageable.getOffset())       // 페이지 시작 위치
                .limit(pageable.getPageSize())     // 페이지 크기
                .fetch();

        // 총 개수 조회
        long total = queryFactory
                .select(qPoint.count())
                .from(qPoint)
                .leftJoin(qPoint.users, qUsers)
                .where(where)
                .fetchOne();

        return new PageImpl<>(tupleList, pageable, total);
    }
}
