package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {

    void deleteBySellerAidIn(List<String> sellerAids);
}
