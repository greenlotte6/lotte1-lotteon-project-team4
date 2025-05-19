package kr.co.lotteon.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderViewDTO {
    private int oid;
    private String orderDate;
    private String shippingStatus;

    private String productName;
    private int productPrice;
    private int quantity;

    private String sellerCompany;
    private String sellerCeo;
}

