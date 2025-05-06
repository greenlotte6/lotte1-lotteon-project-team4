package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "banner")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private int bannerId;

    @Column(name = "banner_name")
    private String bannerName;

    private String size;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column(name = "image_url")
    private String imageUrl;

    private String link;
    private String position;
    private String active;

    @Column(name = "start_day")
    private LocalDate startDay;

    @Column(name = "close_day")
    private LocalDate closeDay;

    @Column(name = "start_at")
    private LocalTime startAt;

    @Column(name = "close_at")
    private LocalTime closeAt;

}

