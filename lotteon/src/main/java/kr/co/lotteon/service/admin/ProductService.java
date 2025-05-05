package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.UsersDTO;
import kr.co.lotteon.entity.Products;
import kr.co.lotteon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 상품 목록 조회 및 페이징 처리
    public PageResponseDTO<ProductDTO> productList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = (Pageable) pageRequestDTO.getPageable("no");

        Page<Products> pageProduct = productRepository.findAll(pageable);

        List<ProductDTO> productDTOList = pageProduct.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());

        int total = (int) pageProduct.getTotalElements();

        return PageResponseDTO
                .<ProductDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(productDTOList)
                .total(total)
                .build();

    }

}
