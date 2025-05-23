package kr.co.lotteon.service.admin;

import kr.co.lotteon.entity.Coupon;
import kr.co.lotteon.entity.CouponIssued;
import kr.co.lotteon.entity.Users;
import kr.co.lotteon.repository.CouponIssuedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponIssuedService {
    private final CouponIssuedRepository couponIssuedRepository;

    public void stopIssuedCoupon(long issueId) {
        CouponIssued couponIssued = couponIssuedRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("해당 발급 쿠폰이 없습니다. issueId = " + issueId));

        couponIssued.setStatus("중단");
        couponIssuedRepository.save(couponIssued);
    }

    public Page<CouponIssued> searchIssuedCoupons(String type, String keyword, Pageable pageable) {
        if (type == null || keyword == null || keyword.trim().isEmpty()) {
            return findAll(pageable);
        }

        switch (type) {
            case "issueId":
                return couponIssuedRepository.findByIssueId(Long.parseLong(keyword), pageable);
            case "couponId":
                return couponIssuedRepository.findByCoupon_CouponIdContaining(keyword, pageable);
            case "couponName":
                return couponIssuedRepository.findByCoupon_CouponNameContaining(keyword, pageable);
            case "userId":
                return couponIssuedRepository.findByUser_UidContaining(keyword, pageable);
            default:
                return findAll(pageable);
        }
    }


    public Page<CouponIssued> findAll(Pageable pageable) {
        return couponIssuedRepository.findAll(pageable);
    }

    public List<CouponIssued> getIssuedCouponsByUid(String uid) {
        return couponIssuedRepository.findByUser_Uid(uid);
    }


    public void issueCoupon(String cno, UserDetails userDetails) {

        String uid = userDetails.getUsername();

        Users users = Users
                .builder()
                .uid(uid)
                .build();

        Coupon coupon = Coupon.builder()
                .couponId(cno)
                .build();

        CouponIssued couponIssued = CouponIssued.builder()
                .coupon(coupon)
                .user(users)
                .status("발급")
                .build();

        couponIssuedRepository.save(couponIssued);
    }
}
