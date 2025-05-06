package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.BannerDTO;
import kr.co.lotteon.entity.Banner;
import kr.co.lotteon.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
                .imageUrl(filePath) // null도 가능
                .link(dto.getLink())
                .position(dto.getPosition())
                .startDay(dto.getStartDay())
                .closeDay(dto.getCloseDay())
                .startAt(dto.getStartAt())
                .closeAt(dto.getCloseAt())
                .active("활동중")
                .build();

        bannerRepository.save(banner);
    }
}
