package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Coupon;
import kr.co.lotteon.repository.custom.ShopRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Page<Coupon> findByCouponIdContaining(String couponId, Pageable pageable);
    Page<Coupon> findByCouponNameContaining(String couponName, Pageable pageable);
    Page<Coupon> findBySellerCompanyContaining(String sellerCompany, Pageable pageable);
}
