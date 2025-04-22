package kr.co.lotteon.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name="company_video")
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    private String youtubeid;
    private String youtubename;
    private String youtubesubtitle;



}
