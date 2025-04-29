package kr.co.lotteon.service.admin;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dao.ShopMapper;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.ShopDTO;
import kr.co.lotteon.entity.Shop;
import kr.co.lotteon.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final ModelMapper modelMapper;

    // 글 목록 조회
    public PageResponseDTO<ShopDTO> findShopList(PageRequestDTO pageRequestDTO) {

        // 페이징 처리를 위한 pageable 객체 생성
        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Shop> pageShop = shopRepository.findAll(pageable);

        List<ShopDTO> shopDTOList = pageShop.getContent().stream()
                                                            .map(ShopDTO::new)
                                                            .collect(Collectors.toList());

        int total = (int) pageShop.getTotalElements();

        log.info("Shop List: {}", shopDTOList);

        return PageResponseDTO.<ShopDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(shopDTOList)
                .total(total)
                .build();

    }

    // 글 목록 검색
    public PageResponseDTO<ShopDTO> searchShop(PageRequestDTO pageRequestDTO) {

        // 페이징 처리를 위한 pageable 객체 생성
        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> pageShop = shopRepository.searchShop(pageRequestDTO, pageable);

        List<ShopDTO> shopDTOList = pageShop.getContent().stream()
                                                            .map(shop -> modelMapper.map(shop, ShopDTO.class))
                                                            .collect(Collectors.toList());

        log.info("Shop List!!!!!!!!!!: {}", shopDTOList);

        int total = (int) pageShop.getTotalElements();

        return PageResponseDTO.<ShopDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(shopDTOList)
                .total(total)
                .build();

    }

    public void delete(List<String> seller_aid) {

        shopRepository.deleteAllById(seller_aid);
    }

    /* 글 목록 검색
    public void searchShop(PageRequestDTO pageRequestDTO) {

    }
    */
}
