package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "product_compliance")
public class ProductCompliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int compliance_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pid")
    private Products products;

    private String status;
    private String tax;
    private String receipt;
    private String biz_type;
    private String origin;

}
