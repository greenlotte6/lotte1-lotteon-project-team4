package kr.co.lotteon.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import kr.co.lotteon.entity.Delivery;
import kr.co.lotteon.entity.Sales;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.repository.SalesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalesRepositoryImplTest {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void test1 () {
        // Given: 샘플 데이터 저장
        Seller seller = new Seller();
        seller.setCompany("TestCompany");
        seller.setBiz_num("123-45-67890");
        em.persist(seller);

        Delivery delivery = new Delivery();
        delivery.setShipping_status("SHIPPED");
        em.persist(delivery);

        Sales sales = new Sales();
        sales.setSeller(seller);
        sales.setDelivery(delivery);
        em.persist(sales);

        em.flush();
        em.clear();

//        // When
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Tuple> result = salesRepository.salesList(pageable);
//
//        // Then
//        assertThat(result.getTotalElements()).isEqualTo(1);
//        Tuple tuple = result.getContent().get(0);
//        assertThat(tuple.get(QSeller.seller.company)).isEqualTo("TestCompany");
//        assertThat(tuple.get(QDelivery.delivery.shippingStatus)).isEqualTo("SHIPPED");
    }

}