package kr.co.lotteon.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.QReview;
import kr.co.lotteon.entity.QUsers;
import kr.co.lotteon.repository.custom.ReviewRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QUsers qUsers = QUsers.users;
    private QReview qReview = QReview.review;

    @Override
    public Page<Tuple> review(Pageable pageable) {

        List<Tuple> tupleList = queryFactory
                .select(qUsers.uid, qReview)
                .from(qReview)
                .join(qReview.users, qUsers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 조회
        long total = queryFactory.select(qReview.count()).from(qReview).fetchOne();

        // 페이징 처리를 위한 페이지 객체 반환
        return new PageImpl<Tuple>(tupleList, pageable, total);
    }
}
