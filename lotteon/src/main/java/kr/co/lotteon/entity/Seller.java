package kr.co.lotteon.entity;

import jakarta.persistence.*;
import kr.co.lotteon.contant.SellerLevel;
import lombok.*;
import org.modelmapper.internal.bytebuddy.utility.dispatcher.JavaDispatcher;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "seller")
public class Seller {

    @Id
    private String aid;

    private String password;
    private String company;
    private String ceo;
    private String biz_num;
    private String osn;
    private String number;
    private String fax;
    private String zip;
    private String addr1;
    private String addr2;
    private String role;


    private String mgmt;
    private String operationText;
    private String statusClass;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SystemStatus status = SystemStatus.READY;


    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private SellerLevel level;

    @PrePersist
    public void prePersist() {
        if (this.level == null) {
            this.level = SellerLevel.BASIC;
        }
    }

}
