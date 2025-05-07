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

    @Column(columnDefinition = "TEXT")
    private String purchaseTerms;

    @Column(columnDefinition = "TEXT")
    private String sellerTerms;

    @Column(columnDefinition = "TEXT")
    private String electronicTerms;

    @Column(columnDefinition = "TEXT")
    private String locationTerms;

    @Column(columnDefinition = "TEXT")
    private String privacyPolicy;
}
