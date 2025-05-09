package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.BannerDTO;
import kr.co.lotteon.entity.Banner;
import kr.co.lotteon.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;

    public void registerBanner(BannerDTO dto) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/uploads/banner/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String filePath = null;
        MultipartFile file = dto.getImageFileUpload();

        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);
            filePath = "/uploads/banner/" + fileName;
        }

        Banner banner = Banner.builder()
                .bannerName(dto.getBannerName())
                .size(dto.getSize())
                .backgroundColor(dto.getBackgroundColor())
                .imageUrl(filePath)
                .link(dto.getLink())
                .position(dto.getPosition())
                .startDay(dto.getStartDay())
                .closeDay(dto.getCloseDay())
                .startAt(dto.getStartAt())
                .closeAt(dto.getCloseAt())
                .active("활성")
                .build();

        bannerRepository.save(banner);
    }

    public List<Banner> getAllBanners() {
        return bannerRepository.findAll(); // 전체 배너 반환
    }

    public void deleteBannersByIds(List<Integer> ids) {
        bannerRepository.deleteAllById(ids);
    }

    public void updateActiveStatus(int bannerId, String active) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new IllegalArgumentException("배너가 존재하지 않습니다."));
        banner.setActive(active);
        bannerRepository.save(banner);
    }

    public List<Banner> getBannersByPosition(String position) {
        return bannerRepository.findByPositionAndActive(position, "활성");
    }

    public void autoDeactivateExpiredBanners() {
        List<Banner> banners = bannerRepository.findByActive("활성");
        LocalDateTime now = LocalDateTime.now();

        for (Banner banner : banners) {
            // 날짜와 시간이 모두 null이 아닌 경우에만 비교 수행
            if (banner.getStartDay() != null && banner.getStartAt() != null &&
                    banner.getCloseDay() != null && banner.getCloseAt() != null) {

                LocalDateTime start = LocalDateTime.of(banner.getStartDay(), banner.getStartAt());
                LocalDateTime end = LocalDateTime.of(banner.getCloseDay(), banner.getCloseAt());

                if (now.isBefore(start) || now.isAfter(end)) {
                    banner.setActive("비활성");
                    bannerRepository.save(banner);
                }
            }
        }
    }




}
