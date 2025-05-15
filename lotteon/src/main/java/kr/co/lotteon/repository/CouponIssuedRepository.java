package kr.co.lotteon.repository;

import kr.co.lotteon.entity.CouponIssued;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CouponIssuedRepository extends JpaRepository<CouponIssued, Long> {
    Page<CouponIssued> findByIssueId(Long issueId, Pageable pageable);

    Page<CouponIssued> findByCoupon_CouponIdContaining(String keyword, Pageable pageable);

    Page<CouponIssued> findByCoupon_CouponNameContaining(String keyword, Pageable pageable);

    Page<CouponIssued> findByUser_UidContaining(String keyword, Pageable pageable);

    List<CouponIssued> findByUser_Uid(String uid);

}
