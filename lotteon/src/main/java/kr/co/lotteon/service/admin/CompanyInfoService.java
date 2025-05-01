package kr.co.lotteon.service.admin;


import kr.co.lotteon.entity.CompanyInfo;
import kr.co.lotteon.entity.SiteInfo;
import kr.co.lotteon.repository.CompanyInfoRepository;
import kr.co.lotteon.repository.SiteInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompanyInfoService {
    private final CompanyInfoRepository companyInfoRepository;

    public CompanyInfo getInfo(int id) {
        return companyInfoRepository.findById(id)
                .orElseGet(() -> {
                    CompanyInfo info = new CompanyInfo();
                    info.setId(1);
                    return info;
                });
    }

    public void updateInfo(CompanyInfo info) {
        info.setId(1);
        companyInfoRepository.save(info);
    }
}
