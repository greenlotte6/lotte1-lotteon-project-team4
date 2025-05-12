//package kr.co.lotteon.repository.impl;
//
//import com.querydsl.core.Tuple;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import kr.co.lotteon.entity.*;
//import kr.co.lotteon.repository.custom.OrdersRepositoryCustom;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Repository
//public class OrdersRepositoryImpl implements OrdersRepositoryCustom {
//
//    private final JPAQueryFactory queryFactory;
//    private QOrders qOrders = QOrders.orders;
//    private QOrderItem qOrderItem = QOrderItem.orderItem;
//    private QUsers qUsers = QUsers.users;
//    private QProducts qProducts = QProducts.products;
//
//
//    @Override
//    public Page<Orders> findByOid(Pageable pageable) {
//        List<Tuple> tupleList = queryFactory
//                .select(qOrders, qOrders.users.uid, qOrders.users.uname, qOrderItem.products.pname, qOrderItem.quantity)
//                .from(qOrders)
//                .join()
//
//        return null; 
//    }
//}
