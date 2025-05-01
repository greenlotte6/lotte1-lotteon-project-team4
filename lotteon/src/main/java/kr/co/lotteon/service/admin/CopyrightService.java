package kr.co.lotteon.service.admin;

import kr.co.lotteon.entity.Copyright;
import kr.co.lotteon.repository.CopyrightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CopyrightService {
    private final CopyrightRepository copyrightRepository;

    public Copyright getInfo(int id) {
        return copyrightRepository.findById(id)
                .orElseGet(() -> {
                    Copyright info = new Copyright();
                    info.setCopyrightId(1);
                    return info;
                });
    }

    public void updateInfo(Copyright info) {
        info.setCopyrightId(1); // 기본 id 고정이라면
        copyrightRepository.save(info);
    }
}
