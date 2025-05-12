package kr.co.lotteon.entity;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Users_uid")
    private Users users;

    private int order_total;
    private String payment;
    private String order_status;

    @CreationTimestamp
    private LocalDateTime order_date;
    private String shipping_status;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems;

}
