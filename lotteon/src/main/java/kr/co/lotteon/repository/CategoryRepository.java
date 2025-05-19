package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIsNullOrderBySortOrderAsc();
    List<Category> findByParentOrderBySortOrderAsc(Category parent);

    List<Category> findByParentIsNull(); // 1차 카테고리 조회
    List<Category> findByParent(Category parent); // 2차 카테고리 조회

    Optional<Category> findById(Long cateId);
//    List<Category> findByParentId(Category parent);
}
