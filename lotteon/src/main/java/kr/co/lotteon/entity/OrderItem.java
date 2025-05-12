package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    private int order_item_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_oid")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Products_pid")
    private Products products;

    private int quantity;

}
