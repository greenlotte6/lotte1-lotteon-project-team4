package kr.co.lotteon.repository;

import kr.co.lotteon.entity.CouponIssued;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponIssuedRepository extends JpaRepository<CouponIssued, Long> {
    List<CouponIssued> findByIssueId(Long issueId);
    List<CouponIssued> findByCoupon_CouponIdContaining(String keyword);
    List<CouponIssued> findByCoupon_CouponNameContaining(String keyword);
    List<CouponIssued> findByUser_UidContaining(String keyword);
}
