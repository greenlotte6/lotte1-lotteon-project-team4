package kr.co.lotteon.dao;

import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.entity.Point;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    public List<PointDTO> selectPoint();

    public void deletePoint(List<Integer> point_id);
}
