package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {

    // job_id는 정확한 숫자 검색
    Page<Recruit> findById(Long id, Pageable pageable);

    // department, type, title은 부분 검색
    Page<Recruit> findByDepartmentContaining(String department, Pageable pageable);

    Page<Recruit> findByTypeContaining(String type, Pageable pageable);

    Page<Recruit> findByTitleContaining(String title, Pageable pageable);
}