package kr.co.lotteon.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.SystemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerDTO {

    private String aid;
    private String password;
    private String company;
    private String ceo;
    private String biz_num;
    private String osn;
    private String number;
    private String fax;
    private String zip;
    private String addr1;
    private String addr2;
    private String role;



    private String mgmt;
    private String operationText;
    private String statusClass;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SystemStatus status = SystemStatus.READY;


    public SellerDTO(Seller seller) {
        this.status = seller.getStatus() != null ? seller.getStatus() : SystemStatus.READY;
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
