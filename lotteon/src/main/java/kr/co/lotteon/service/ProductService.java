package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.entity.Products;
import kr.co.lotteon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("ProductService")
public class ProductService {

    private final ProductRepository productRepository;
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

}
