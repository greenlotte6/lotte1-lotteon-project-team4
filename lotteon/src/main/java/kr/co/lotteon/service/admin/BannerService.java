package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.BannerDTO;
import kr.co.lotteon.entity.Banner;
import kr.co.lotteon.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
}
