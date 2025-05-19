package kr.co.lotteon.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.ProductRepository;
import kr.co.lotteon.repository.custom.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QProducts qProducts = QProducts.products;
    private QReview qReview = QReview.review;

    @Override
    public Page<Tuple> productList(Pageable pageable) {
        List<Tuple> tupleList = queryFactory
                .select(qProducts, qReview.rating)
                .from(qReview)
                .join(qReview.products, qProducts)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 조회
        long total = queryFactory.select(qProducts.count()).from(qProducts).fetchOne();

        // 페이징 처리를 위한 페이지 객체 반환
        return new PageImpl<Tuple>(tupleList, pageable, total);
    }


}
