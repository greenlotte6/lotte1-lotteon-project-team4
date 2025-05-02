package kr.co.lotteon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LotteonApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotteonApplication.class, args);
    }

}
