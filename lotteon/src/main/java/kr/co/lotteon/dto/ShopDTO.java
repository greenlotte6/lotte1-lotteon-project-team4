package kr.co.lotteon.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
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
    private String aid;

    // 추가필드
    private String company;
    private String ceo;
    private String biz_num;
    private String osn;
    private String number;

}
