package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String addr;
    private String role;
    private String seller_id;
}
