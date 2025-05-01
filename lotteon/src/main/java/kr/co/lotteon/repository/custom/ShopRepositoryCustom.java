package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.ShopDTO;
import kr.co.lotteon.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepositoryCustom {

    public Page<Tuple> searchShop(PageRequestDTO pageRequestDTO, Pageable pageable);

//    public void registerShop(ShopDTO shopDTO);

}
