package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Integer> {
    Page<Faq> findByType1(String type1, Pageable pageable);
    Page<Faq> findByType1AndType2(String type1, String type2, Pageable pageable);
        List<Faq> findByType1(String type1);
        List<Faq> findByType1AndType2(String type1, String type2);
}
