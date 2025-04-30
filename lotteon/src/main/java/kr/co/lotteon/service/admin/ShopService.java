package kr.co.lotteon.service.admin;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dao.ShopMapper;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Sales;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.Shop;
import kr.co.lotteon.entity.SystemStatus;
import kr.co.lotteon.repository.SalesRepository;
import kr.co.lotteon.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShopService {

    private final SalesRepository salesRepository;
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final ModelMapper modelMapper;

    // 글 목록 조회
    public PageResponseDTO<ShopDTO> findShopList(PageRequestDTO pageRequestDTO) {

        // 페이징 처리를 위한 pageable 객체 생성
        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Shop> pageShop = shopRepository.findAll(pageable);

//        List<ShopDTO> shopDTOList = pageShop.getContent().stream()
//                                                            .map(ShopDTO::new)
//                                                            .collect(Collectors.toList());

        List<ShopDTO> shopDTOList = pageShop.getContent().stream()
                .map(shop -> {
                    ShopDTO shopDTO = new ShopDTO(shop);

                    SystemStatus status = shopDTO.getStatus();
                    if (status != null) {
                        switch (status) {
                            case OPERATING:
                                shopDTO.setOperationText("[운영중]");
                                shopDTO.setStatusClass("green");
                                break;
                            case READY:
                                shopDTO.setOperationText("[운영준비]");
                                shopDTO.setStatusClass("blue");
                                break;
                            case STOPPED:
                                shopDTO.setOperationText("[운영중지]");
                                shopDTO.setStatusClass("red");
                                break;
                        }
                    } else {
                        shopDTO.setOperationText("[운영준비]");
                        shopDTO.setStatusClass("blue");
                    }

                    return shopDTO;
                }).collect(Collectors.toList());

        int total = (int) pageShop.getTotalElements();

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

        List<ShopDTO> shopDTOList = pageShop.getContent().stream().map(tuple -> {

            Shop shop = tuple.get(0, Shop.class);
            String company = tuple.get(1, String.class);
            String ceo = tuple.get(2, String.class);
            String biz_num = tuple.get(3, String.class);
            String osn = tuple.get(4, String.class);
            String number = tuple.get(5, String.class);

            ShopDTO shopDTO = modelMapper.map(shop, ShopDTO.class);
            shopDTO.setCompany(company);
            shopDTO.setCeo(ceo);
            shopDTO.setBiz_num(biz_num);
            shopDTO.setOsn(osn);
            shopDTO.setNumber(number);

            return shopDTO;
        }).toList();

        int total = (int) pageShop.getTotalElements();

        log.info("total {}", total);

        return PageResponseDTO.<ShopDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(shopDTOList)
                .total(total)
                .build();
    }

    public void delete(List<String> seller_aid) {

        shopRepository.deleteAllById(seller_aid);
    }

    // 매출 현황 목록 조회
    public PageResponseDTO<SalesDTO> salesList(PageRequestDTO pageRequestDTO) {

//        Pageable pageable = pageRequestDTO.getPageable("no");
//
//        Page<Tuple> salesPage = salesRepository.salesList(pageable);
//
//        List<SalesDTO> salesDTOList = salesPage.stream().map(tuple -> {
//            Date order_date = tuple.get(0, Date.class);
//            String order_item_id = tuple.get(1, String.class);
//            Integer order_status = tuple.get(2, Integer.class);
//            Integer shipping_status = tuple.get(3, Integer.class);
//            Integer order_total = tuple.get(4, Integer.class);
//            Integer order_total_sum = tuple.get(5, Integer.class);
//
//            SalesDTO salesDTO = modelMapper.map(tuple.get(0, SalesDTO.class), SalesDTO.class);
//            salesDTO.setOrder_date(order_date);
//            salesDTO.setOrder_item_id(order_item_id);
//            salesDTO.setOrder_status(order_status);
//            salesDTO.setOrder_total(order_total);
//            salesDTO.setOrder_total_sum(order_total_sum);
//            return salesDTO;
//
//
//        })


        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> salesPage = salesRepository.salesList(pageable);

        List<SalesDTO> salesDTOList = salesPage.stream().map(tuple -> {
            Sales sales = tuple.get(0, Sales.class);
            String company = tuple.get(1, String.class);
            String biz_num = tuple.get(2, String.class);
            String shipping_status = tuple.get(3, String.class);

            SalesDTO salesDTO = modelMapper.map(sales, SalesDTO.class);
            salesDTO.setCompany(company);
            salesDTO.setBiz_num(biz_num);
            salesDTO.setShipping_status(shipping_status);

            return salesDTO;
        }).toList();

        log.info("salesDTOList: {}", salesDTOList);

        int total = (int) salesPage.getTotalElements();

        return PageResponseDTO.<SalesDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(salesDTOList)
                .total(total)
                .build();

    }

}
