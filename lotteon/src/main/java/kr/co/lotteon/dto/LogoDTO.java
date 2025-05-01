package kr.co.lotteon.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoDTO {

    private int logoId;

    private String header_file;

    private String footer_file;

    private String favicon;
}
