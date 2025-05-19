package kr.co.lotteon.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderView {
    private int oid;
    private String orderDate;
    private String shippingStatus;
    private String productName;
    private int productPrice;
    private int quantity;
    private String sellerCompany;
}

