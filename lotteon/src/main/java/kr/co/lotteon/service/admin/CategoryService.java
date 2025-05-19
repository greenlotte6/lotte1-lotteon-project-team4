package kr.co.lotteon.service.admin;


import jakarta.transaction.Transactional;
import kr.co.lotteon.dto.CategoryDTO;
import kr.co.lotteon.entity.Category;
import kr.co.lotteon.entity.CompanyInfo;
import kr.co.lotteon.repository.CategoryRepository;
import kr.co.lotteon.repository.CompanyInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll(Sort.by("sortOrder"));
    }

    @Transactional
    public void updateCategoryStructure(List<CategoryDTO> categories) {
        for (CategoryDTO parentDto : categories) {
            Category parent = (parentDto.getId() != null) ?
                    categoryRepository.findById(parentDto.getId()).orElse(new Category()) :
                    new Category();

            parent.setCateName(parentDto.getName());
            parent.setSortOrder(parentDto.getSortOrder());
            parent.setParent(null);
            Category savedParent = categoryRepository.save(parent);

            for (CategoryDTO childDto : parentDto.getChildren()) {
                Category child = (childDto.getId() != null) ?
                        categoryRepository.findById(childDto.getId()).orElse(new Category()) :
                        new Category();

                child.setCateName(childDto.getName());
                child.setSortOrder(childDto.getSortOrder());
                child.setParent(savedParent);
                categoryRepository.save(child);
            }
        }
    }

    public List<CategoryDTO> getHierarchicalCategories() {
        List<Category> all = categoryRepository.findAll(Sort.by("sortOrder"));
        Map<Long, CategoryDTO> map = new HashMap<>();
        List<CategoryDTO> result = new ArrayList<>();

        // 모든 Category를 CategoryDTO로 변환해서 Map에 저장
        for (Category category : all) {
            CategoryDTO dto = new CategoryDTO(category.getCateId(), category.getCateName(), category.getSortOrder());
            map.put(category.getCateId(), dto);
        }

        // 계층 구조 구성
        for (Category category : all) {
            CategoryDTO dto = map.get(category.getCateId());
            if (category.getParent() != null) {
                // 부모가 있으면 부모의 children에 추가
                map.get(category.getParent().getCateId()).getChildren().add(dto);
            } else {
                // 부모가 없으면 최상위(1차 카테고리)
                result.add(dto);
            }
        }

        return result;
    }

    // 1차 카테고리 목록 조회
    public List<CategoryDTO> getCategories1() {

        List<Category> categoryList = categoryRepository.findByParentIsNull();

        return categoryList.stream()
                .map(cate -> CategoryDTO.builder()
                        .cateId(cate.getCateId())
                        .name(cate.getCateName())
                        .sortOrder(cate.getSortOrder())
                        .build())
                .collect(Collectors.toList());
    }

    // 2차 카테고리 목록 조회
    public List<CategoryDTO> getCategories2(Category parent) {

        List<Category> categoryList = categoryRepository.findByParent(parent);

        return categoryList.stream()
                .map(cate -> CategoryDTO.builder()
                        .cateId(cate.getCateId())
                        .name(cate.getCateName())
                        .parent(cate.getParent() != null ? cate.getParent().getCateId() : null)
                        .sortOrder(cate.getSortOrder())
                        .build())
                .collect(Collectors.toList());
    }



}
