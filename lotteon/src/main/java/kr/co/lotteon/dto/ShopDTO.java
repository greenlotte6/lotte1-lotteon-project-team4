package kr.co.lotteon.dto;

import com.querydsl.core.Tuple;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import kr.co.lotteon.entity.Shop;
import kr.co.lotteon.entity.SystemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopDTO {

    private int no;
    private String shop_id;
    private String operation;
    private String mgmt;
    private String seller_aid;

    // 추가필드
    private String aid;
    private String company;
    private String ceo;
    private String biz_num;
    private String osn;
    private String number;

    private SystemStatus status; // SystemStatus 필드

    private String operationText;
    private String statusClass;

//    public void setOperationText(String text) {
//        this.operationText = text;
//    }
//
//    public void setStatusClass(String css) {
//        this.statusClass = css;
//    }


    public ShopDTO(Shop shop) {
        this.no = shop.getNo();
        this.shop_id = shop.getShop_id();
        this.operation = shop.getOperation();
        this.mgmt = shop.getMgmt();
        this.aid = shop.getSeller().getAid();
        this.company = shop.getSeller().getCompany();
        this.ceo = shop.getSeller().getCeo();
        this.biz_num = shop.getSeller().getBiz_num();
        this.osn = shop.getSeller().getOsn();
        this.number = shop.getSeller().getNumber();
        this.seller_aid = shop.getSeller().getAid();
        this.status = shop.getStatus();
    }

}
