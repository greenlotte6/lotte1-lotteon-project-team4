package kr.co.lotteon.repository;

import kr.co.lotteon.entity.CouponIssued;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponIssuedRepository extends JpaRepository<CouponIssued, Long> {
    Page<CouponIssued> findByIssueId(Long issueId, Pageable pageable);

    Page<CouponIssued> findByCoupon_CouponIdContaining(String keyword, Pageable pageable);

    Page<CouponIssued> findByCoupon_CouponNameContaining(String keyword, Pageable pageable);

    Page<CouponIssued> findByUser_UidContaining(String keyword, Pageable pageable);
}
