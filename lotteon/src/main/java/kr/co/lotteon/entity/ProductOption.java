package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "product_option")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int option_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Products_pid")
    private Products products;

    private String option_name;

//    @OneToMany(mappedBy = "product_option", cascade = CascadeType.ALL)
//    private List<ProductOptionItem> optionItems;
}
