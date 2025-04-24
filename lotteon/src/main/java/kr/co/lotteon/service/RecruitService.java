package kr.co.lotteon.service;

import kr.co.lotteon.entity.Recruit;
import kr.co.lotteon.repository.RecruitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RecruitService {

    private final RecruitRepository recruitRepository;

    public RecruitService(RecruitRepository recruitRepository) {
        this.recruitRepository = recruitRepository;
    }

    // 채용 공고 저장
    public void saveRecruit(Recruit recruit) {
        recruitRepository.save(recruit);
    }

    // 모든 채용 공고 조회 (페이징 처리)
    public Page<Recruit> findAllRecruits(Pageable pageable) {
        return recruitRepository.findAll(pageable);
    }

    // 검색 조건에 맞는 채용 공고 조회 (검색기능 추가)
    public Page<Recruit> searchRecruits(String searchType, String searchKeyword, Pageable pageable) {
        searchKeyword = searchKeyword.trim();  // 공백 제거

        switch (searchType) {
            case "job_id":
                return recruitRepository.findByIdContaining(searchKeyword, pageable);
            case "department":
                return recruitRepository.findByDepartmentContaining(searchKeyword, pageable);
            case "type":
                return recruitRepository.findByTypeContaining(searchKeyword, pageable);
            case "title":
                return recruitRepository.findByTitleContaining(searchKeyword, pageable);
            default:
                throw new IllegalArgumentException("Invalid search type: " + searchType);
        }

    }

    // 특정 채용 공고 조회
    public Recruit findRecruitById(Long id) {
        return recruitRepository.findById(id).orElse(null);
    }

    // 채용 공고 삭제
    public void deleteRecruits(List<Long> jobIds) {
        if (jobIds == null || jobIds.isEmpty()) {
            throw new RuntimeException("유효하지 않은 jobIds가 전달되었습니다.");
        }

        List<Recruit> recruitsToDelete = recruitRepository.findAllById(jobIds);


        if (recruitsToDelete.isEmpty()) {
            throw new RuntimeException("해당 jobIds에 해당하는 채용 공고를 찾을 수 없습니다. 전달된 jobIds: " + jobIds);
        }

        recruitRepository.deleteAll(recruitsToDelete);
    }
}