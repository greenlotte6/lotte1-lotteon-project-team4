package kr.co.lotteon.dto;

import kr.co.lotteon.entity.Delivery;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersDTO {

    private int oid;
    private String Users_uid;
    private int order_total;
    private String payment;
    private String order_status;
    private LocalDateTime order_date;
    private String shipping_status;
    private String recipient;

    // 추가 필드
    private String uname;
    private String hp;
    private String pname;
    private int quantity;
    private Orders orders;
    private String addr1;
    private String addr2;
    private String zip;
    private String delivery_company;
    private String delivery_num;
    private String other;

    private String img_file_1;
    private int pid;
    private String company;
    private int price;
    private String discount;
    private int delivery_free;
    private String recipientHp;


    private int total_price;
    private int total_discount;
    private int total_delivery_free;
    private int total_pay;




}
