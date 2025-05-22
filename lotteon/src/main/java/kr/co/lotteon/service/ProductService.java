package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("ProductService")
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionItemRepository productOptionItemRepository;
    private final CategoryRepository categoryRepository;
    private final ProductComplianceRepository productComplianceRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    // 상품 목록 페이지 조회 및 페이징 처리
    @Cacheable(value = "productList")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageableNotSort();

        Page<Tuple> productsPage;

        if (pageRequestDTO.getCateId() != null) {
            // 카테고리로 필터된 상품 목록 조회
            productsPage = productRepository.productListByCategory(pageRequestDTO.getCateId(), pageable);
        } else {
            // 전체 상품 목록 조회
            productsPage = productRepository.productList(pageable);
        }

        List<ProductDTO> productDTOS = productsPage.getContent().stream().map(tuple -> {
            Products products = tuple.get(0, Products.class);
            Double avgRating = tuple.get(1, Double.class);
            Long reviewCount = tuple.get(2, Long.class);

            ProductDTO productDTO = modelMapper.map(products, ProductDTO.class);
            productDTO.setRating(avgRating != null ? avgRating : 0);
            productDTO.setReview_count(reviewCount != null ? reviewCount.intValue() : 0);
            productDTO.setDiscountPrice(productDTO.getDiscountedPrice());

            return productDTO;
        }).toList();

        int total = (int) productsPage.getTotalElements();

        return PageResponseDTO.<ProductDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(productDTOS)
                .total(total)
                .build();
    }

    // 상품 상세페이지
    public ProductDTO view(int pid) {
        Optional<Products> optProducts = productRepository.findByPid(pid);

        if (optProducts.isPresent()) {
            Products products = optProducts.get();

            ProductDTO productDTO = modelMapper.map(products, ProductDTO.class);
            productDTO.setDiscountPrice(productDTO.getDiscountedPrice());

            Double avgRating = reviewRepository.getAverageRating(pid);
            productDTO.setRating(avgRating != null ? avgRating : 0.0);

            return productDTO;
        }else {
            throw new NoSuchElementException("Product not found");
        }
    }

    public PageResponseDTO<ProductDTO> getProductListByCategory(Long cateId, PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageableNotSort();
        Page<Products> productPage = productRepository.findByCategoryCateId(cateId, pageable);

        List<ProductDTO> dtoList = productPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ProductDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) productPage.getTotalElements())
                .build();
    }

    // 상품 정보 고시
    public ProductComplianceDTO productCompliance(int pid) {
        Optional<ProductCompliance> optCompliance = productComplianceRepository.findById(pid);

        if (optCompliance.isPresent()) {
            ProductCompliance productCompliance = optCompliance.get();
            ProductComplianceDTO productComplianceDTO = modelMapper.map(productCompliance, ProductComplianceDTO.class);

            return productComplianceDTO;
        }else {
            throw new NoSuchElementException("Product compliance not found");
        }
    }

    // 상품 리뷰 조회 및 페이징 처리
    public PageResponseDTO<ReviewDTO> Review(PageRequestDTO pageRequestDTO, int pid) {
        Pageable pageable = pageRequestDTO.getPageableNotSort();

        Page<Tuple> tuplePage = reviewRepository.review(pageable, pid);

        List<ReviewDTO> reviewDTOS = tuplePage.getContent().stream().map(tuple -> {
            String uid = tuple.get(0, String.class);
            Review review = tuple.get(1, Review.class);
            String pname = tuple.get(2, String.class);

            ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
            reviewDTO.setUsers_uid(uid);
            reviewDTO.setPname(pname);
            return reviewDTO;
        }).toList();

        int total = (int) tuplePage.getTotalElements();

        return PageResponseDTO.<ReviewDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(reviewDTOS)
                .total(total)
                .build();


    }


    // 쿠폰 선택
//    public void coupon()

    // 카테고리 조회
//    public CategoryDTO getAllCategories(int option_id, int item_id) {
//
//        Optional<ProductOption> optProductOption = productOptionRepository.findById(option_id);
//        Optional<ProductOptionItem> optProductOptionItem = productOptionItemRepository.findById(item_id);
//
//        if (optProductOption.isPresent()) {
//            ProductOption productOption = optProductOption.get();
//            ProductOptionDTO productOptionDTO = modelMapper.map(productOption, ProductOptionDTO.class);
//            if (optProductOptionItem.isPresent()) {
//                ProductOptionItem productOptionItem = optProductOptionItem.get();
//                ProductOptionItemDTO productOptionItemDTO = modelMapper.map(productOptionItem, ProductOptionItemDTO.class);
//            }
//
//        }else {
//            throw new NoSuchElementException("Category not found");
//        }
//
//    }

}
