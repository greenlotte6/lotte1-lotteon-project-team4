package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

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
    private int category_cate_id;
    private int point_rate;
    private int cart_item_item_id;
    private int productCompliance_compliance_id;
    private int review_count;

    // 할인 가격 필드
    private BigDecimal discountPrice;


    // 추가 필드
    private Double rating;

    public BigDecimal getDiscountedPrice() {
        // 1. 원가를 BigDecimal로 변환 (int → BigDecimal)
        BigDecimal originalPrice = BigDecimal.valueOf(price);

        // 2. 할인액 계산
        // - 할인율을 퍼센트로 변환 (예: 10% → 0.10)
        BigDecimal discountAmount = originalPrice.multiply(BigDecimal.valueOf(discount)) // 원가 * 할인율
                .divide(BigDecimal.valueOf(100)); // 100으로 나눠서 퍼센트 계산

        // 3. 최종 가격 계산 (원가 - 할인액)
        return originalPrice.subtract(discountAmount);
    }


}
