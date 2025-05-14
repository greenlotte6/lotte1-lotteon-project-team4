package kr.co.lotteon.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import kr.co.lotteon.contant.SellerLevel;
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




    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private SellerLevel level;
}
