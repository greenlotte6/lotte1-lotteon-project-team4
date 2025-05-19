package kr.co.lotteon.dao;

import kr.co.lotteon.dto.OrderViewDTO;
import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderMapper {

    List<OrdersDTO> orderTotal(@Param("oid") Long oid);
    List<OrderViewDTO> selectOrderViewsByUid(@Param("uid") String uid);

}
