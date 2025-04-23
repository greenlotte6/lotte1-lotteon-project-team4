package kr.co.lotteon.service;

import kr.co.lotteon.entity.Recruit;
import kr.co.lotteon.repository.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruitService {
    private final RecruitRepository recruitRepository;

    public RecruitService(RecruitRepository recruitRepository) {
        this.recruitRepository = recruitRepository;
    }

    public void saveRecruit(Recruit recruit) {
        recruitRepository.save(recruit);
    }
}