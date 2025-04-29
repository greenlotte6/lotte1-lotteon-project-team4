package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    private int delivery_id;
    private int order_oid;
    private String delivery_company;
    private String delivery_num;
    private String other;
    private String shipping_status;

    @CreationTimestamp
    private LocalDateTime delivery_at;


}
