package kr.co.lotteon.service.admin;

import kr.co.lotteon.entity.Copyright;
import kr.co.lotteon.entity.Support;
import kr.co.lotteon.repository.CopyrightRepository;
import kr.co.lotteon.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;

    public Support getInfo(int id) {
        return supportRepository.findById(id)
                .orElseGet(() -> {
                    Support info = new Support();
                    info.setSupportId(1);
                    return info;
                });
    }

    public void updateInfo(Support info) {
        info.setSupportId(1); // 기본 id 고정이라면
        supportRepository.save(info);
    }
}
