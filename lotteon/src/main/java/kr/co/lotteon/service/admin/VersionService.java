package kr.co.lotteon.service.admin;

import kr.co.lotteon.entity.Coupon;
import kr.co.lotteon.entity.Version;
import kr.co.lotteon.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VersionService {

    private final VersionRepository versionRepository;

    public List<Version> getAllVersions() {
        return versionRepository.findAll();
    }

    public void deleteVersions(List<Integer> ids) {
        versionRepository.deleteAllById(ids);
    }
}
