package kr.co.lotteon.dto;

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

    // 추가 필드
    private String uname;
    private String pname;
    private int quantity;

}
