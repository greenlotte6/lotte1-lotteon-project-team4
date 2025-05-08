package kr.co.lotteon.repository;

import kr.co.lotteon.entity.ProductOption;
import kr.co.lotteon.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
    List<ProductOption> findByProducts(Products products);
}
