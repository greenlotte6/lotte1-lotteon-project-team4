package kr.co.lotteon.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.custom.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QProducts qProducts = QProducts.products;
    private QProductCompliance qProductCompliance = QProductCompliance.productCompliance;
    private QReview qReview = QReview.review;
    private QCategory qCategory = QCategory.category;

    @Override
    public List<ProductDTO> main(int pid) {


        return List.of();
    }

    @Override
    public Page<Tuple> productList(Pageable pageable) {

        // 동적으로 정렬 조건을 담을 리스트 생성
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        // Pageable 객체에서 정렬 정보(Sort)를 가져와서 하나씩 확인
        for (Sort.Order order : pageable.getSort()) {
            // 오름차순인지 내림차순인지 확인
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            // 어떤 속성으로 정렬할 것인지 가져오기 (예: "hits", "price")
            String property = order.getProperty();

            // 정렬 속성에 따라 QueryDSL이 이해할 수 있는 정렬 방식(OrderSpecifier)을 생성하여 리스트에 담기
            switch (property) {
                case "hits": // 판매 많은 순
                    orderSpecifiers.add(new OrderSpecifier(direction, qProducts.hits));
                    break;
                case "price": // 가격 순
                    orderSpecifiers.add(new OrderSpecifier(direction, qProducts.price));
                    break;
                case "rating": // 평점 높은 순
                    orderSpecifiers.add(new OrderSpecifier(direction, qReview.rating.avg()));
                    break;
                case "review_count": // 후기 많은 순
                    orderSpecifiers.add(new OrderSpecifier(direction, qReview.count()));
                    break;
                case "p_created_at": // 최근 등록 순 (기본값으로 처리)
                default:
                    orderSpecifiers.add(new OrderSpecifier(direction, qProducts.p_created_at));
                    break;
            }
        }

        // QueryDSL 쿼리 생성 (정렬 조건은 아직 적용 안 함)
        JPAQuery<Tuple> query = queryFactory
                .select(qProducts, qReview.rating.avg(), qReview.count(), qProducts.category.cateId)
                .from(qProducts)
                .leftJoin(qProducts.review, qReview) // 리뷰가 없는 상품도 포함하기 위해 leftJoin 사용
                .join(qProducts.category, qCategory)
                .groupBy(qProducts.pid)
                .offset(pageable.getOffset()) // 페이징 시작 위치 설정
                .limit(pageable.getPageSize()); // 한 페이지에 보여줄 개수 설정

        // 만약 정렬 조건이 하나라도 있다면 쿼리에 적용
        if (!orderSpecifiers.isEmpty()) {
            query.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));
        }

        // 쿼리 실행 결과를 리스트로 가져오기
        List<Tuple> tupleList = query.fetch();

        // 전체 상품 개수 조회 (leftJoin으로 인해 Products 기준으로 count)
        long total = queryFactory.select(qProducts.count()).from(qProducts).fetchOne();

        // 페이징 처리된 결과와 전체 개수를 담아서 반환
        return new PageImpl<>(tupleList, pageable, total);
    }

    @Override
    public Page<Tuple> productListByCategory(Long cateId, Pageable pageable) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            switch (property) {
                case "hits":
                    orderSpecifiers.add(new OrderSpecifier(direction, qProducts.hits));
                    break;
                case "price":
                    orderSpecifiers.add(new OrderSpecifier(direction, qProducts.price));
                    break;
                case "rating":
                    orderSpecifiers.add(new OrderSpecifier(direction, qReview.rating.avg()));
                    break;
                case "review_count":
                    orderSpecifiers.add(new OrderSpecifier(direction, qReview.count()));
                    break;
                case "p_created_at":
                default:
                    orderSpecifiers.add(new OrderSpecifier(direction, qProducts.p_created_at));
                    break;
            }
        }

        JPAQuery<Tuple> query = queryFactory
                .select(qProducts, qReview.rating.avg(), qReview.count())
                .from(qProducts)
                .leftJoin(qProducts.review, qReview)
                .where(qProducts.category.cateId.eq(cateId))
                .groupBy(qProducts.pid)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (!orderSpecifiers.isEmpty()) {
            query.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));
        }

        List<Tuple> tupleList = query.fetch();


        long total = queryFactory
                .select(qProducts.count())
                .from(qProducts)
                .where(qProducts.category.cateId.eq(cateId))
                .fetchOne();

        return new PageImpl<>(tupleList, pageable, total);
    }

}
