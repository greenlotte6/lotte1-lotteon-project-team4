package kr.co.lotteon.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoDTO {

    private int logoId;

    private String headerFile;
    private String footerFile;
    private String favicon;

    private MultipartFile headerFileUpload;
    private MultipartFile footerFileUpload;
    private MultipartFile faviconUpload;
}
