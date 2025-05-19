package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Category;
import kr.co.lotteon.entity.ProductOption;
import kr.co.lotteon.entity.ProductOptionItem;
import kr.co.lotteon.entity.Products;
import kr.co.lotteon.repository.CategoryRepository;
import kr.co.lotteon.repository.ProductOptionItemRepository;
import kr.co.lotteon.repository.ProductOptionRepository;
import kr.co.lotteon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service("ProductService")
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionItemRepository productOptionItemRepository;
    private final ModelMapper modelMapper;

    // 상품 목록 페이지 조회 및 페이징 처리
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageableNotSort();

        Page<Tuple> productsPage = productRepository.productList(pageable);

        List<ProductDTO> productDTOS = productsPage.getContent().stream().map(tuple -> {
            Products products = tuple.get(0, Products.class);
            int rating = tuple.get(1, Integer.class);

            ProductDTO productDTO = modelMapper.map(products, ProductDTO.class);
            productDTO.setRating(rating);
            return productDTO;
        }).toList();

        // 전체 게시물 갯수
        int total = (int) productsPage.getTotalElements();

        return PageResponseDTO
                .<ProductDTO>builder()
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

            return productDTO;
        }else {
            throw new NoSuchElementException("Product not found");
        }
    }

    // 카테고리 조회
//    public CategoryDTO getAllCategories(int option_id, int item_id) {

//        Optional<ProductOption> optProductOption = productOptionRepository.findById(option_id);
//        Optional<ProductOptionItem> optProductOptionItem = productOptionItemRepository.findById(item_id);
//
//        if (optProductOption.isPresent()) {
//            ProductOption productOption = optProductOption.get();
//            ProductOptionDTO productOptionDTO = modelMapper.map(productOption, ProductOptionDTO.class);
//            if (optProductOptionItem.isPresent()) {
//                ProductOptionItem productOptionItem = optProductOptionItem.get();
//                ProductOptionItemDTO productOptionItemDTO = modelMapper.map(productOptionItem, ProductOptionItemDTO.class);
//            } else {
//                throw new NoSuchElementException("Product option item not found");
//            }
//
//        }else {
//            throw new NoSuchElementException("Category not found");
//        }

//    }

}
