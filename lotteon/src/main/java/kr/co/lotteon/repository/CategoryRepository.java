package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIsNullOrderBySortOrderAsc();
    List<Category> findByParentOrderBySortOrderAsc(Category parent);

}
