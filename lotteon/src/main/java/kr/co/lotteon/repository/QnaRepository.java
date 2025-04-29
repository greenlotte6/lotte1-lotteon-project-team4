package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Integer> {
    List<Qna> findByQnaType1(String qnaType1);
    // Qna 엔티티에 대해 필요한 추가적인 메서드를 작성할 수 있습니다.
}
