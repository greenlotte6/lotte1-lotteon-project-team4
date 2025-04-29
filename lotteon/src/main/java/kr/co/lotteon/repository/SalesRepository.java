package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Sales;
import kr.co.lotteon.repository.custom.SalesRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales,Integer>, SalesRepositoryCustom {
}
