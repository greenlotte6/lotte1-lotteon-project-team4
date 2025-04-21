package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "proucts")
public class products {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    private String imgFile1;
    private String imgFile2;
    private String imgFile3;
    private String detailFile1;

    private Integer pcode;

    private String pname;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal price;
    private String discount;
    private Integer stock;
    private String company;
    private String hits;
    private String mgmtVoucher;

    private Integer categoryId;
    private String brand;

    private LocalDateTime pCreatedAt;
    private LocalDateTime pUpdatedAt;

    private String maker;
    private Integer deliveryFree;

    private Integer categoryCateId;
    private Float pointRate;

    private Integer cartItemItemId;
}
