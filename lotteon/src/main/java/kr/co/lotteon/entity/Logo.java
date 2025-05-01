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
@Table(name = "logo")
public class Logo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logo_id")
    private int logoId;

    @Column(name = "header_file")
    private String headerFile;

    @Column(name = "footer_file")
    private String footerFile;

    private String favicon;
}
