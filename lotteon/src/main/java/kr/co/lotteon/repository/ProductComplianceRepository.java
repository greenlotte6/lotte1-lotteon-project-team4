package kr.co.lotteon.repository;

import kr.co.lotteon.entity.ProductCompliance;
import kr.co.lotteon.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductComplianceRepository extends JpaRepository<ProductCompliance, Integer> {
    void deleteByProductsPidIn(List<Integer> pid);

    ProductCompliance findByProducts(Products products);
}
