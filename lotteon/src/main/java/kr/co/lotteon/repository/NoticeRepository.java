package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Notice;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    Page<Notice> findByNoticeType(String type, Pageable pageable);
}
