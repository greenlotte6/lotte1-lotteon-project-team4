package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {  // Integer에서 Long으로 변경
    List<Qna> findByQnaType1(String qnaType1);
    Page<Qna> findByQnaType1(String qnaType1, Pageable pageable);
    Page<Qna> findByQnaType1AndUserOrderByDateDesc(String qnaType1, Users uid, Pageable pageable);
}
