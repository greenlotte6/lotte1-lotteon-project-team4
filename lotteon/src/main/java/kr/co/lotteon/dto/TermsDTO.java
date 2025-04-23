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
    private String purchase_terms;
    private String seller_terms;
    private String electronic_terms;
    private String location_terms;
    private String privacy_policy;

}
