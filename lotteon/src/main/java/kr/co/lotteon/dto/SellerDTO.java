package kr.co.lotteon.dto;

import jakarta.persistence.Id;
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
    private String seller_id;

}
