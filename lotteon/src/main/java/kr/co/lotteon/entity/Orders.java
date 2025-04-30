package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    private int oid;

    private String Users_uid;
    private int order_total;
    private String payment;
    private String order_status;

    @CreationTimestamp
    private LocalDateTime order_date;
    private String shipping_status;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems;

}
