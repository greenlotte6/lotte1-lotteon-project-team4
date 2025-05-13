package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {
    long countByAid(String aid);
    Optional<Seller> findByAid(String aid);
}
