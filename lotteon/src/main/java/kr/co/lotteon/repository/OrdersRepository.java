package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.repository.custom.OrdersRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>, OrdersRepositoryCustom {

}
