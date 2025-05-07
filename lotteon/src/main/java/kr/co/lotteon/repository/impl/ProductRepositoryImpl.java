package kr.co.lotteon.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.ProductRepository;
import kr.co.lotteon.repository.custom.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QProducts qProducts = QProducts.products;
    private QCategory qCategory = QCategory.category;
    private QProductOption qProductOption = QProductOption.productOption;
    private QProductOptionItem qProductOptionItem = QProductOptionItem.productOptionItem;
    private QProductCompliance qProductCompliance = QProductCompliance.productCompliance;

    @Override
    public void modifyProduct(ProductDTO productDTO) {

//        Products products = queryFactory
//                .select(qCategory,
//                        qProducts.pname, qProducts.description, qProducts.company, qProducts.price, qProducts.discount, qProducts.point, qProducts.stock, qProducts.delivery_free, qProducts.brand, qProducts.img_file_1, qProducts.img_file_2, qProducts.img_file_3, qProducts.detaile_file_1,
//                        qProductOption.option_name, qProductOptionItem.item_name,
//                        qProductCompliance)
//                .from(qProducts)

    }
}
