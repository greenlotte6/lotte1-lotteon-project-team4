package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepositoryCustom {
    Page<Tuple> selectPoint(Pageable pageable);
    Page<Tuple> searchPoint(PageRequestDTO pageRequestDTO, Pageable pageable);
}
