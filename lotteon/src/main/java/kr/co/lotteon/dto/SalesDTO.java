package kr.co.lotteon.dto;

import com.querydsl.core.Tuple;
import kr.co.lotteon.entity.Sales;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesDTO {

    private int sales_id;
    private String admin_aid;
    private int order_count;
    private int pay_completed;
    private int shipped;
    private int confirmed;
    private int order_total;
    private int sales;

    // 추가 필드
    private String company;
    private String biz_num;
    private String shipping_status;

    public SalesDTO(Sales sales) {
        this.sales_id = sales.getSales_id();
        this.admin_aid = sales.getSeller().getAid();
        this.order_count = sales.getOrder_count();
        this.pay_completed = sales.getPay_completed();
        this.shipped = sales.getShipped();
        this.confirmed = sales.getConfirmed();
        this.order_total = sales.getOrder_total();
        this.sales = sales.getSales();
        this.company = sales.getSeller().getCompany();
        this.biz_num = sales.getSeller().getBiz_num();
        this.shipping_status = sales.getDelivery().getShipping_status();


    }

}
