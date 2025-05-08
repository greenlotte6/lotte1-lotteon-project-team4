package kr.co.lotteon.repository;

import kr.co.lotteon.entity.ProductOption;
import kr.co.lotteon.entity.ProductOptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionItemRepository extends JpaRepository<ProductOptionItem, Integer> {
    List<ProductOptionItem> findByProductOption(ProductOption option);
}
