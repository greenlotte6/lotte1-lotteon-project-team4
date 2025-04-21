package kr.co.lotteon.dto;

public class CompanyVideoDTO {

    private String youtubeid;
    private String youtubename;
    private String youtubesubtitle;

    // 생성자
    public CompanyVideoDTO(String youtubeid, String youtubename, String youtubesubtitle) {
        this.youtubeid = youtubeid;
        this.youtubename = youtubename;
        this.youtubesubtitle = youtubesubtitle;
    }

}
