package kr.co.lotteon.dao;

import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<OrdersDTO> orderTotal(int oid);

}
