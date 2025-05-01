package kr.co.lotteon.dao;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.entity.Point;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface MemberMapper {

    public Page<Tuple> selectPoint(Pageable pageable);

    public void deletePoint(List<Integer> point_id);
}
