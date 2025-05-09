package kr.co.lotteon.scheduler;

import kr.co.lotteon.service.admin.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BannerScheduler {

    private final BannerService bannerService;

    @Scheduled(cron = "0 * * * * *") // 매 분마다
    public void checkBannerStatus() {
        bannerService.autoDeactivateExpiredBanners();
    }

}
