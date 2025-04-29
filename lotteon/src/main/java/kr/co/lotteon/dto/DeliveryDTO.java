package kr.co.lotteon.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryDTO {

    private int delivery_id;
    private int order_oid;
    private String delivery_company;
    private String delivery_num;
    private String other;
    private String shipping_status;
    private LocalDateTime delivery_at;
}
