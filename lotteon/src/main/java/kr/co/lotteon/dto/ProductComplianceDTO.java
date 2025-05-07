package kr.co.lotteon.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import kr.co.lotteon.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductComplianceDTO {

    private int compliance_id;
    private int Products_pid;
    private String status;
    private String tax;
    private String receipt;
    private String biz_type;
    private String origin;

}
