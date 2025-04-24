package kr.co.lotteon.entity;

import jakarta.persistence.Column;
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
    @Column(name = "terms_id")
    private int termsId;

    private String purchaseTerms;
    private String sellerTerms;
    private String electronicTerms;
    private String locationTerms;
    private String privacyPolicy;
}
