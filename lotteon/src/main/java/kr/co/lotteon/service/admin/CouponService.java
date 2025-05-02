package kr.co.lotteon.service.admin;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import kr.co.lotteon.entity.Coupon;
import kr.co.lotteon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * 전체 쿠폰 조회 - 페이징 포함
     */
    public Page<Coupon> getCouponList(int pg) {
        Pageable pageable = PageRequest.of(pg - 1, 10, Sort.by("couponId").descending());
        return couponRepository.findAll(pageable);
    }

    /**
     * 전체 쿠폰 조회 - 검색 포함
     */
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

    /**
     * 쿠폰 등록
     */
    @Transactional
    public void registerCoupon(Coupon coupon) {
        coupon.setCouponId(null);
        long newCouponId = generateUniqueCouponId();
        coupon.setCouponId(String.valueOf(newCouponId));

        em.persist(coupon);
    }

    /**
     * 쿠폰 전체 리스트
     */
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    /**
     * 매일 자정 자동 실행 - 사용기한 만료 쿠폰 상태 "종료" 처리
     */
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 실행
    @Transactional
    public void expireCouponsByDate() {
        LocalDate today = LocalDate.now();

        List<Coupon> expiredCoupons = couponRepository.findAll().stream()
                .filter(coupon -> {
                    try {
                        if (coupon.getValidTo() == null || coupon.getStatus() == null) return false;

                        LocalDate endDate = LocalDate.parse(coupon.getValidTo()); // yyyy-MM-dd 그대로 파싱
                        return endDate.isBefore(today) && !"종료".equals(coupon.getStatus());
                    } catch (Exception e) {
                        log.warn("유효기간 파싱 실패: couponId={}, validTo={}", coupon.getCouponId(), coupon.getValidTo());
                        return false;
                    }
                })
                .collect(Collectors.toList());

        log.info("⏰ 쿠폰 상태 자동 검사 시작");

        expiredCoupons.forEach(coupon -> {
            coupon.setStatus("종료");
            couponRepository.save(coupon); // 명시적으로 저장
            log.info("만료 쿠폰 자동 종료 처리: {}", coupon.getCouponName());
        });
    }


    /**
     * 발급 쿠폰 수동 중단
     */
    public void stopIssuedCoupon(String issueNumber) {
        // 구현 예정
    }

    /**
     * 중복 방지용 쿠폰 ID 생성기
     */
    private long generateUniqueCouponId() {
        long couponId;
        do {
            couponId = generateCouponId();
        } while (couponRepository.existsById(couponId));
        return couponId;
    }

    /**
     * 쿠폰 ID 생성기 (10 + 9자리 난수)
     */
    private long generateCouponId() {
        String prefix = "10";
        int randomNum = (int)(Math.random() * 1_000_000_000);
        return Long.parseLong(prefix + String.format("%09d", randomNum));
    }
}
