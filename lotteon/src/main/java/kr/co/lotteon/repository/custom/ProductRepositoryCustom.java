package kr.co.lotteon.repository.custom;

import kr.co.lotteon.dto.ProductDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryCustom {

    void modifyProduct(ProductDTO productDTO);

}
