package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "Products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    private String img_file_1;
    private String img_file_2;
    private String img_file_3;
    private String detaile_file_1;
    private String pname;
    private String description;
    private int price;
    private String discount;
    private int point;
    private int stock;
    private String company;
    private String hits;
    private String mgmt;
    private int category_id;
    private String brand;

    @CreationTimestamp
    private LocalDate p_created_at;
    @UpdateTimestamp
    private LocalDate p_updates_at;

    private String maker;
    private int delivery_free;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_cate_id")
    private Category category;

    private int point_rate;

    // 양방향 1:1 관계 매핑
    @OneToOne(mappedBy = "products", orphanRemoval = true)
    private ProductCompliance productCompliance;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cart_item_item_id")
//    private CartItem cartItem;
//
//    @OneToOne(mappedBy = "Products", cascade = CascadeType.ALL)
//    @JoinColumn(name = "productCompliance_compliance_id")
//    private ProductCompliance productCompliance;
//
//    @OneToMany(mappedBy = "Products", cascade = CascadeType.ALL)
//    private List<ProductOption> productOption;

}