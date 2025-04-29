package kr.co.lotteon.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.QDelivery;
import kr.co.lotteon.entity.QSales;
import kr.co.lotteon.entity.QSeller;
import kr.co.lotteon.entity.QShop;
import kr.co.lotteon.repository.custom.ShopRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.swing.text.JTextComponent;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Tuple> searchShop(PageRequestDTO pageRequestDTO, Pageable pageable) {

        QShop shop = QShop.shop;
        QSeller seller = QSeller.seller;

        BooleanBuilder builder = new BooleanBuilder();

        // 검색 조건 처리
        if (pageRequestDTO.getSearchType() != null && pageRequestDTO.getKeyword() != null) {
            String keyword = pageRequestDTO.getKeyword();

            switch (pageRequestDTO.getSearchType()) {
                case "company":
                    builder.and(seller.company.containsIgnoreCase(keyword));
                    break;
                case "ceo":
                    builder.and(seller.ceo.containsIgnoreCase(keyword));
                    break;
                case "biz_num":
                    builder.and(seller.biz_num.containsIgnoreCase(keyword));
                    break;
                case "number":
                    builder.and(seller.number.containsIgnoreCase(keyword));
                    break;
            }
        }

        List<Tuple> content = queryFactory
                .select(shop, seller.company, seller.ceo, seller.biz_num, seller.osn, seller.number)
                .from(shop)
                .join(shop.seller, seller)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(shop.count())
                .from(shop)
                .join(seller)
                .on(shop.seller.aid.eq(seller.aid))
                .where(builder)
                .fetchOne();


        return new PageImpl<Tuple>(content, pageable, total);
    }

}
