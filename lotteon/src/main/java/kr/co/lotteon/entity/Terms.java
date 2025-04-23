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

    private String purchase_terms;
    private String seller_terms;
    private String electronic_terms;
    private String location_terms;
    private String privacy_policy;
}
