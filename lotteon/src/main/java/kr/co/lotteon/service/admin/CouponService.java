package kr.co.lotteon.service.admin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import kr.co.lotteon.entity.Coupon;
import kr.co.lotteon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Page<Coupon> getCouponList(int pg) {
        Pageable pageable = PageRequest.of(pg - 1, 10, Sort.by("couponId").descending());
        return couponRepository.findAll(pageable);
    }

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void registerCoupon(Coupon coupon) {
        coupon.setCouponId(null);
        long newCouponId = generateUniqueCouponId();
        coupon.setCouponId(String.valueOf(newCouponId));

        em.persist(coupon); // save() 대신 persist() 사용
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Page<Coupon> getCouponsWithSearch(int pg, String searchType, String searchKeyword) {
        Pageable pageable = PageRequest.of(pg - 1, 10, Sort.by(Sort.Direction.DESC, "couponId"));

        if (searchType != null && searchKeyword != null && !searchKeyword.isEmpty()) {
            switch (searchType) {
                case "couponId":
                    return couponRepository.findByCouponIdContaining(searchKeyword, pageable);
                case "couponName":
                    return couponRepository.findByCouponNameContaining(searchKeyword, pageable);
                case "sellerCompany":
                    return couponRepository.findBySellerCompanyContaining(searchKeyword, pageable);
            }
        }
        return couponRepository.findAll(pageable);
    }

    private long generateUniqueCouponId() {
        long couponId;
        do {
            couponId = generateCouponId();
        } while (couponRepository.existsById(couponId));
        return couponId;
    }

    private long generateCouponId() {
        String prefix = "10";
        int randomNum = (int)(Math.random() * 1_000_000_000);
        return Long.parseLong(prefix + String.format("%09d", randomNum));
    }

    public void stopIssuedCoupon(String issueNumber) {

    }

}
