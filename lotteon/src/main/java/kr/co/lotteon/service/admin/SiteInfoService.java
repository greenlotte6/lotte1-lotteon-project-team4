package kr.co.lotteon.service.admin;


import kr.co.lotteon.entity.SiteInfo;
import kr.co.lotteon.repository.SiteInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SiteInfoService {
    private final SiteInfoRepository siteInfoRepository;

    public SiteInfo getInfo(int id) {
        return siteInfoRepository.findById(id)
                .orElseGet(() -> new SiteInfo(1, "기본 제목", "기본 부제", LocalDateTime.now()));
    }

    public void updateInfo(SiteInfo info) {
        info.setId(1);
        siteInfoRepository.save(info);
    }
}
