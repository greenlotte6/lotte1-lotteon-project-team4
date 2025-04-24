package kr.co.lotteon.dto;

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

}
