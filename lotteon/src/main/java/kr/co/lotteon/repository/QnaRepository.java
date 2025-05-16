package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByQnaType1(String qnaType1);
    Page<Qna> findByQnaType1(String qnaType1, Pageable pageable);
    Page<Qna> findByQnaType1AndUserOrderByDateDesc(String qnaType1, Users uid, Pageable pageable);
    // 선택삭제 기능을 위한 메서드 (ID로 Qna 삭제)
    void deleteByQnaId(Long qnaId);
    Page<Qna> findByQnaType1AndQnaType2(String qnaType1, String qnaType2, Pageable pageable);
    List<Qna> findByUser(Users user);
    List<Qna> findByUserUid(String uid);



}
