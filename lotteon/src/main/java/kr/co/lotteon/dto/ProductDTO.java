package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO implements Serializable {

    private int pid;
    private String img_file_1;
    private String img_file_2;
    private String img_file_3;
    private String detaile_file_1;
    private String pname;
    private String description;
    private int price;
    private int discount;
    private int point;
    private int stock;
    private String company;
    private String hits;
    private String mgmt;
    private String brand;
    private LocalDate p_created_at;
    private LocalDate p_updates_at;
    private String maker;
    private int delivery_free;
    private long category_cate_id;
    private int point_rate;
    private int cart_item_item_id;
    private int productCompliance_compliance_id;
    private int review_count;

    // 할인 가격 필드
    private BigDecimal discountPrice;

    // 추가 필드
    private Double rating;
//    private String status;
//    private String tax;
//    private String receipt;
//    private String biz_type;
//    private String origin;


    public BigDecimal getDiscountedPrice() {
        // 1. 원가를 BigDecimal로 변환
        BigDecimal originalPrice = BigDecimal.valueOf(price);

        // 2. 할인액 계산 (원가 * 할인율 / 100)
        BigDecimal discountAmount = originalPrice
                .multiply(BigDecimal.valueOf(discount))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP); // 소수점 둘째 자리까지 계산 후 반올림

        // 3. 최종 가격 계산 (원가 - 할인액), 결과는 정수로 반올림
        return originalPrice.subtract(discountAmount)
                .setScale(0, RoundingMode.HALF_UP); // 소수점 없이 반올림
    }


}
