package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepositoryCustom {
    public Page<Tuple> salesList(Pageable pageable);
}
