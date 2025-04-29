package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "sales")
public class Sales {

    @Id
    private int sales_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_aid")
    private Seller seller;

    private int order_count;
    private int pay_completed;
    private int shipped;
    private int confirmed;
    private int order_total;
    private int sales;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
}
