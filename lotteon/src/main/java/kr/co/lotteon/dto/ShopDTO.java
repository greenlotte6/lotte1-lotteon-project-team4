package kr.co.lotteon.dto;

import com.querydsl.core.Tuple;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import kr.co.lotteon.entity.Seller;
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
    private String seller_aid;

    // 추가필드
    private String zip;
    private String aid;
    private String company;
    private String ceo;
    private String biz_num;
    private String osn;
    private String number;

    private SystemStatus status; // SystemStatus 필드
    private String mgmt;
    private String operationText;
    private String statusClass;

//    public void setOperationText(String text) {
//        this.operationText = text;
//    }
//
//    public void setStatusClass(String css) {
//        this.statusClass = css;
//    }


    public ShopDTO(Seller seller) {
        this.aid = seller.getAid();
        this.seller_aid = seller.getAid();
        this.company = seller.getCompany();
        this.ceo = seller.getCeo();
        this.biz_num = seller.getBiz_num();
        this.osn = seller.getOsn();
        this.number = seller.getNumber();
        this.zip = seller.getZip();

        this.status = seller.getStatus();

        if (this.status == SystemStatus.OPERATING) {
            this.operationText = "[운영중]";
            this.statusClass = "green";
        } else if (this.status == SystemStatus.STOPPED) {
            this.operationText = "[운영중지]";
            this.statusClass = "red";
        } else {
            this.operationText = "[운영준비]";
            this.statusClass = "blue";
        }
    }


}
