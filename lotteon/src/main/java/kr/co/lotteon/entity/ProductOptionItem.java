package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "product_option_item")
public class ProductOptionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int item_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_option_id")
    private ProductOption productOption;

    private String item_name;

}
