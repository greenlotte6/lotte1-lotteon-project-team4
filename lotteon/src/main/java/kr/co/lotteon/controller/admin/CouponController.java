package kr.co.lotteon.controller.admin;

import kr.co.lotteon.entity.Coupon;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.repository.CouponRepository;
import kr.co.lotteon.service.admin.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/coupon")
public class CouponController {

    private final CouponService couponService;
    private final CouponRepository couponRepository;

    @GetMapping("/list")
    public String list(@RequestParam(value = "pg", defaultValue = "1") int pg,
                       @RequestParam(value = "searchType", required = false) String searchType,
                       @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                       Model model) {

        Page<Coupon> coupons = couponService.getCouponsWithSearch(pg, searchType, searchKeyword);

        model.addAttribute("coupons", coupons.getContent());
        model.addAttribute("currentPage", coupons.getNumber() + 1);
        model.addAttribute("totalPages", coupons.getTotalPages());
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchKeyword", searchKeyword);

        return "/admin/coupon/list";
    }


    @GetMapping("/issued")
    public String issued() {
        return "/admin/coupon/issued";
    }

    @PostMapping("/register")
    @ResponseBody
    public String registerCoupon(@RequestBody Coupon coupon) {
        couponService.registerCoupon(coupon);
        return "success";
    }

    @PostMapping("/end/{couponId}")
    @ResponseBody
    public String endCoupon(@PathVariable Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 없습니다. ID=" + couponId));

        coupon.setStatus("종료"); // 상태를 "종료"로 변경
        couponRepository.save(coupon);

        return "success";
    }
}

