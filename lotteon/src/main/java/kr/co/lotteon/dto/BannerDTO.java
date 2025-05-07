package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerDTO {

    private int bannerId;
    private String bannerName;
    private String size;
    private String backgroundColor;
    private String imageUrl;
    private String link;
    private String position;
    private String active;
    private LocalDate startDay;
    private LocalDate closeDay;
    private LocalTime startAt;
    private LocalTime closeAt;

    private MultipartFile imageFileUpload;
}
