package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Integer> {
    Page<Faq> findByType1(String type1, Pageable pageable);
    Page<Faq> findByType1AndType2(String type1, String type2, Pageable pageable);

    List<Faq> findByType1AndType2(String type1, String type2);

    List<Faq> findByType1(String type1);
    List<Faq> findByType2(String type2);

    @Query("SELECT DISTINCT f.type2 FROM Faq f WHERE f.type1 = :type1")
    List<String> findDistinctType2ByType1(@Param("type1") String type1);

    // 추가: type1, type2를 LIKE로 조회하는 메소드
    @Query("SELECT f FROM Faq f WHERE f.type1 LIKE %:type1% AND f.type2 LIKE %:type2%")
    List<Faq> findByType1AndType2Like(@Param("type1") String type1, @Param("type2") String type2);
}
