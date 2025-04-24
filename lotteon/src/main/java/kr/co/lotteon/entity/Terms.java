package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "terms")
public class Terms {

    @Id
    private int terms_id;

    private String purchaseTerms;
    private String sellerTerms;
    private String electronicTerms;
    private String locationTerms;
    private String privacyPolicy;
}
