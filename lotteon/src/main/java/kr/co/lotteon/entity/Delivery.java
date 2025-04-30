package kr.co.lotteon.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    private int delivery_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_oid")
    private Orders orders;

    private String delivery_company;
    private String delivery_num;
    private String other;
    private String shipping_status;

    @CreationTimestamp
    private LocalDateTime delivery_at;


}
