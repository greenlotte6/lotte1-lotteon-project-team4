package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Integer> {
    List<Qna> findByQnaType1(String qnaType1);
    // Qna 엔티티에 대해 필요한 추가적인 메서드를 작성할 수 있습니다
    Page<Qna> findByQnaType1(String qnaType1, Pageable pageable);

    // qnaType1과 userUid에 해당하는 Qna 리스트 반환 (페이지네이션 적용, 최신글 먼저)
    Page<Qna> findByQnaType1AndUserUidOrderByDateDesc(String qnaType1, String userUid, Pageable pageable);
}