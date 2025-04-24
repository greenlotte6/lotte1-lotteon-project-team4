package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    // 기존: findByJobIdContaining
    Page<Recruit> findByIdContaining(String jobId, Pageable pageable);
    Page<Recruit> findByDepartmentContaining(String department, Pageable pageable);
    Page<Recruit> findByTypeContaining(String type, Pageable pageable);
    Page<Recruit> findByTitleContaining(String title, Pageable pageable);
}