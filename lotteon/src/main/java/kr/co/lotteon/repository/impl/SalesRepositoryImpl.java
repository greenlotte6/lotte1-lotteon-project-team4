package kr.co.lotteon.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.entity.QDelivery;
import kr.co.lotteon.entity.QSales;
import kr.co.lotteon.entity.QSeller;
import kr.co.lotteon.repository.custom.SalesRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class SalesRepositoryImpl implements SalesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Tuple> salesList(Pageable pageable) {

        QDelivery delivery = QDelivery.delivery;
        QSales sales = QSales.sales1;
        QSeller seller = QSeller.seller;

        List<Tuple> tupleList = queryFactory
                .select(sales, seller.company, seller.biz_num, delivery.shipping_status)
                .from(sales)
                .join(sales.delivery, delivery)
                .join(sales.seller, seller)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy((seller.company.asc()))
                .fetch();

        // 총 개수 조회
        long total = queryFactory.select(sales.count()).from(sales).fetchOne();


        return new PageImpl<Tuple>(tupleList, pageable, total);
    }
}
