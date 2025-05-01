package kr.co.lotteon.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.QPoint;
import kr.co.lotteon.entity.QUsers;
import kr.co.lotteon.repository.PointRepository;
import kr.co.lotteon.repository.custom.PointRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PointRepositoryImpl implements PointRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QUsers qUsers = QUsers.users;
    private QPoint qPoint = QPoint.point;

    @Override
    public Page<Tuple> selectPoint(Pageable pageable) {

        List<Tuple> tupleList = queryFactory
                                    .select(qPoint, qUsers.uid, qUsers.uname)
                                    .from(qPoint)
                                    .join(qPoint.users, qUsers)
                                    .offset(pageable.getOffset())
                                    .limit(pageable.getPageSize())
                                    .orderBy(qPoint.no.desc())
                                    .fetch();

        // 총 개수 조회
        long total = queryFactory.select(qPoint.count()).from(qPoint).fetchOne();

        // 페이징 처리를 위한 페이지 객체 반환
        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> searchPoint(PageRequestDTO pageRequestDTO, Pageable pageable) {

        String searchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanExpression expression = null;

        if(searchType.equals("uid")){
            expression = qUsers.uid.contains(keyword);
        }else if(searchType.equals("uname")){
            expression = qUsers.uname.contains(keyword);
        }else if(searchType.equals("email")){
            expression = qUsers.email.contains(keyword);
        }else if(searchType.equals("hp")){
            expression = qUsers.hp.contains(keyword);
        }

        List<Tuple> tupleList = queryFactory
                .select(qPoint, qUsers.uid, qUsers.uname)
                .from(qPoint)
                .join(qPoint.users, qUsers)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qPoint.no.desc())
                .fetch();

        // 총 개수 조회
        long total = queryFactory.select(qPoint.count()).from(qPoint).fetchOne();

        // 페이징 처리를 위한 페이지 객체 반환
        return new PageImpl<Tuple>(tupleList, pageable, total);
    }
}
