package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.repository.custom.OrdersRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>, OrdersRepositoryCustom {

    Page<Orders> findByOidContaining(String keyword, Pageable pageable);
    Page<Orders> findByUsers_UidContaining(String keyword, Pageable pageable);
    Page<Orders> findByUsers_UnameContaining(String keyword, Pageable pageable);
    List<Orders> findByUsers_Uid(String uid);

}
