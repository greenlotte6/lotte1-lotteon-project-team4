package kr.co.lotteon.dao;

import kr.co.lotteon.dto.ShopDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopMapper {

    public void deleteShop(List<String> seller_aid);

}
