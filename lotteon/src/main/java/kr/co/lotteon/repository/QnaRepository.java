package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Integer> {
    List<Qna> findByUserUid(String userUid);
}
