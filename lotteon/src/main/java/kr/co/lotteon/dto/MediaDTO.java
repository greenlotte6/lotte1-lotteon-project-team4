package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
    private String title;
    private String description;
    private String url;
    private String thumbnail;
}
