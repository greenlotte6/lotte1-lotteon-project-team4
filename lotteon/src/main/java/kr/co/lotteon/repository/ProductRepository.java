package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Products;
import kr.co.lotteon.repository.custom.ProductRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Products, Integer>, ProductRepositoryCustom {

    Page<Products> findAll(Pageable pageable);

    Page<Products> findByPidContaining(String keyword, Pageable pageable);
    Page<Products> findByPnameContaining(String keyword, Pageable pageable);
    Page<Products> findByCompanyContaining(String keyword, Pageable pageable);
}
