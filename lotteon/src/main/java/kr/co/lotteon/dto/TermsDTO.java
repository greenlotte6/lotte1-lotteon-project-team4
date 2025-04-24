package kr.co.lotteon.dto;

import kr.co.lotteon.entity.Terms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermsDTO {

    private int terms_id;
    private String purchaseTerms;
    private String sellerTerms;
    private String electronicTerms;
    private String locationTerms;
    private String privacyPolicy;

    // 약관을 위한 부분
    private String section1;
    private String section2;
    private String section3;
    private String section4;
    private String section5;
    private String section6;

    // 엔티티 변환
    public Terms toEntity() {

        return Terms.builder()
                .terms_id(terms_id)
                .purchaseTerms(purchaseTerms)
                .sellerTerms(sellerTerms)
                .electronicTerms(electronicTerms)
                .locationTerms(locationTerms)
                .privacyPolicy(privacyPolicy)
                .build();

    }
}
