package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

    Page<Products> findAll(Pageable pageable);
}
