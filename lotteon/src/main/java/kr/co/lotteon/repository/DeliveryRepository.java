package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    Delivery findByOrders_oid(int oid);
}
