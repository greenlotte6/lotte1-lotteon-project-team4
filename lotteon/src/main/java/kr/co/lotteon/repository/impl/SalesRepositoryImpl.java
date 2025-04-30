package kr.co.lotteon.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.custom.SalesRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class SalesRepositoryImpl implements SalesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

//    @Override
//    public Page<Tuple> salesList(Pageable pageable) {
//
//        QOrders qOrders = QOrders.orders;
//        QOrderItem qOrderItem = QOrderItem.orderItem;
//        QDelivery qDelivery = QDelivery.delivery;
//        QSales qSales = QSales.sales1;
//        QSeller qSeller = QSeller.seller;
//
//        List<Tuple> tupleList = queryFactory
//                .select(
//                        qOrders.order_date,  // DATE(o.order_date)
//
//                        qOrderItem.order_item_id,
//
//                        qOrders.order_status
//                                .when("결제완료").then(1)
//                                .otherwise(0)
//                                .sum(),
//
//                        qDelivery.shipping_status
//                                .when("배송중").then(1)
//                                .otherwise(0)
//                                .sum(),
//
//                        qDelivery.shipping_status
//                                .when("배송완료").then(1)
//                                .otherwise(0)
//                                .sum(),
//
//                        qOrders.order_status
//                                .when("구매확정").then(1)
//                                .otherwise(0)
//                                .sum(),
//
//                        qOrders.order_total.sum(),
//
//                        new CaseBuilder()
//                                .when(qOrders.order_status.eq("결제완료"))
//                                .then(qOrders.order_total)
//                                .otherwise(0)
//                                .sum()
//                )
//                .from(qSales)
//                .join(qSales.delivery, qDelivery)
//                .join(qSales.seller, qSeller)
//                .join(qDelivery.orders, qOrders)
//                .join(qOrders.orderItems, qOrderItem)
//                .groupBy(qOrders.order_date)
//                .orderBy(qOrders.order_date.asc())
//                .fetch();
//
//        // 총 개수 조회
//        long total = queryFactory.select(qSales.count()).from(qSales).fetchOne();
//
//        return new PageImpl<Tuple>(tupleList, pageable, total);
//    }

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
