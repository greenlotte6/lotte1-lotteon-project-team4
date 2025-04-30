package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "company_info")
public class CompanyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "ceo")
    private String ceo;

    @Column(name = "company_number")
    private String companyNumber;

    @Column(name = "ecommerce_reg")
    private String ecommerceReg;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;
}
