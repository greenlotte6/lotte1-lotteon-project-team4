package kr.co.lotteon.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "shop")
public class Shop {

    private int no;

    @Id
    private String shop_id;

    private String operation;
    private String mgmt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_aid")
    private Seller seller;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SystemStatus status;


}
