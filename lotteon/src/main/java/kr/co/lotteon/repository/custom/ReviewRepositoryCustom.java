package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepositoryCustom {
    Page<Tuple> review(Pageable pageable, int pid);
    Double getAverageRating(int pid);
}
