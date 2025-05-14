package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepositoryCustom {
    Page<Tuple> findByOid(Pageable pageable);
//    List<Tuple> findByOid(int oid);
}
