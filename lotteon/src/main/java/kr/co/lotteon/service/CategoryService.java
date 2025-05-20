package kr.co.lotteon.service;

import kr.co.lotteon.entity.Category;
import kr.co.lotteon.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("CategoryService")
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 경로 가져오기
    public List<Category> getCategoryPath(Long cateId) {
        List<Category> path = new ArrayList<>();

        // 현재 카테고리 조회
        Category current = categoryRepository.findById(cateId).orElse(null);

        log.info("current: {}", current);

        // 최상위 카테고리까지 반복
        while (current != null) {
            path.add(0, current); // 상위 카테고리를 앞에 추가
            current = current.getParent(); // 부모 카테고리로 이동
        }

        log.info("path: {}", path);

        return path;
    }

    // 특정 카테고리의 하위 카테고리 목록 조회
//    public List<Category> getSubcategories(Long parentId) {
//        // 부모 카테고리 조회
//        Category parent = categoryRepository.findById(parentId).orElse(null);
//        if (parent == null) return List.of(); // 부모가 없는 경우 빈 리스트 반환
//
//        // 자식 카테고리 조회
//        return categoryRepository.findByParent(parent);
//    }

}
