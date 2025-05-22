package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepositoryCustom {

    List<ProductDTO> main(int pid);

    Page<Tuple> productList(Pageable pageable);
    Page<Tuple> productListByCategory(Long cateId, Pageable pageable);

}
