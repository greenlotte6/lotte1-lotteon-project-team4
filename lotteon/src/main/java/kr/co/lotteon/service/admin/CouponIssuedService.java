package kr.co.lotteon.service.admin;

import kr.co.lotteon.entity.CouponIssued;
import kr.co.lotteon.repository.CouponIssuedRepository;
import lombok.RequiredArgsConstructor;
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

    public List<CouponIssued> searchIssuedCoupons(String type, String keyword) {
        if (type == null || keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }

        switch (type) {
            case "issueId":
                return couponIssuedRepository.findByIssueId(Long.parseLong(keyword));
            case "couponId":
                return couponIssuedRepository.findByCoupon_CouponIdContaining(keyword);
            case "couponName":
                return couponIssuedRepository.findByCoupon_CouponNameContaining(keyword);
            case "userId":
                return couponIssuedRepository.findByUser_UidContaining(keyword);
            default:
                return couponIssuedRepository.findAll();
        }
    }

    public List<CouponIssued> findAll() {
        return couponIssuedRepository.findAll();
    }



}
