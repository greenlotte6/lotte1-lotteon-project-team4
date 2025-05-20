package kr.co.lotteon.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.entity.QProducts;
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
    private QProducts qProducts = QProducts.products;

    @Override
    public Page<Tuple> review(Pageable pageable, int pid) {

        List<Tuple> tupleList = queryFactory
                .select(qUsers.uid, qReview, qProducts.pname)
                .from(qReview)
                .join(qReview.users, qUsers)
                .join(qReview.products, qProducts)
                .where(qReview.products.pid.eq(pid))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 조회
        long total = queryFactory.select(qReview.count()).from(qReview).where(qReview.products.pid.eq(pid)).fetchOne();

        // 페이징 처리를 위한 페이지 객체 반환
        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

    public Double getAverageRating(int pid) {
        return queryFactory
                .select(qReview.rating.avg())
                .from(qReview)
                .where(qReview.products.pid.eq(pid))
                .fetchOne();
    }
}
