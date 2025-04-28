package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id

    @Column(name = "coupon_id")
    private String couponId;

    @Column(name = "coupon_type")
    private String couponType;

    @Column(name = "coupon_name")
    private String couponName;
    private String benefit;

    @Column(name = "valid_from")
    private String validFrom;

    @Column(name = "valid_to")
    private String validTo;
    private String sellerCompany;

    @Column(name = "issued_count")
    private Integer issuedCount;

    @Column(name = "used_count")
    private Integer usedCount;
    private String status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String other;


}
